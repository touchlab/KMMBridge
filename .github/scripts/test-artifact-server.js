#!/usr/bin/env node
/**
 * Throwaway stand-in for Touchlab's private test-artifact server (api.touchlab.dev).
 *
 * Implements just the two endpoints KMMBridge's TestUploadArtifactManager needs:
 *   POST /store   -> stores the request body, returns {"url": "/infoadmin/streamTestZip/<id>.zip"}
 *   GET  /file/<id>.zip -> serves the stored bytes back
 *
 * Used by build_mac.yml so CI (including fork PRs, which never get repo secrets)
 * can exercise the real upload/download round-trip.
 */
const http = require("http");
const fs = require("fs");
const os = require("os");
const path = require("path");
const crypto = require("crypto");

const STORAGE_DIR = fs.mkdtempSync(path.join(os.tmpdir(), "kmmbridge-test-artifacts-"));

function log(message) {
  process.stderr.write(`[test-artifact-server] ${message}\n`);
}

const server = http.createServer((req, res) => {
  log(`${req.method} ${req.url}`);

  if (req.method === "POST" && req.url === "/store") {
    const chunks = [];
    req.on("data", (chunk) => chunks.push(chunk));
    req.on("end", () => {
      const body = Buffer.concat(chunks);
      const fileId = `${crypto.randomUUID()}.zip`;
      fs.writeFileSync(path.join(STORAGE_DIR, fileId), body);

      const reply = JSON.stringify({ url: `/file/${fileId}` });
      res.writeHead(200, {
        "Content-Type": "application/json",
        "Content-Length": Buffer.byteLength(reply),
      });
      res.end(reply);
    });
    return;
  }

  const prefix = "/file/";
  if (req.method === "GET" && req.url.startsWith(prefix)) {
    const fileId = req.url.slice(prefix.length);
    const filePath = path.join(STORAGE_DIR, fileId);
    if (fileId.includes("..") || !fs.existsSync(filePath) || !fs.statSync(filePath).isFile()) {
      res.writeHead(404);
      res.end();
      return;
    }

    const data = fs.readFileSync(filePath);
    res.writeHead(200, {
      "Content-Type": "application/octet-stream",
      "Content-Length": data.length,
    });
    res.end(data);
    return;
  }

  res.writeHead(404);
  res.end();
});

const port = process.argv[2] ? parseInt(process.argv[2], 10) : 8089;
server.listen(port, "127.0.0.1", () => {
  console.log(`[test-artifact-server] listening on 127.0.0.1:${port}, storage=${STORAGE_DIR}`);
});
