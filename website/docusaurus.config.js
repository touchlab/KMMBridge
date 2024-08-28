// @ts-check
// Note: type annotations allow type checking and IDEs autocompletion

const lightCodeTheme = require('./intellijstylelight.js');
const darkCodeTheme = require('./intellijstyle.js');
const touchlabConfig = require('./touchlabconfig.js');

/** @type {import('@docusaurus/types').Config} */
const config = {
  title: touchlabConfig.docusaurusConfig.projectName,
  tagline: touchlabConfig.docusaurusConfig.tagline,
  url: touchlabConfig.docusaurusConfig.url,
  baseUrl: '/',
  onBrokenLinks: 'throw',
  onBrokenMarkdownLinks: 'warn',
  favicon: 'img/favicon.ico',

  // GitHub pages deployment config.
  // If you aren't using GitHub pages, you don't need these.
  organizationName: 'touchlab', // Usually your GitHub org/user name.
  projectName: touchlabConfig.docusaurusConfig.projectName, // Usually your repo name.

  // Even if you don't use internalization, you can use this field to set useful
  // metadata like html lang. For example, if your site is Chinese, you may want
  // to replace "en" with "zh-Hans".
  i18n: {
    defaultLocale: 'en',
    locales: ['en'],
  },

  // plugins: ['@docusaurus/plugin-content-tldocs'],
  plugins: [
    [
      '@docusaurus/plugin-google-gtag',
      {
        trackingID: touchlabConfig.extraConfig.trackingID,
        anonymizeIP: false,
      },
    ],
    async function myPlugin(context, options) {
      return {
        name: "docusaurus-tailwindcss-omg",
        configurePostCss(postcssOptions) {
          // Appends TailwindCSS and AutoPrefixer.
          postcssOptions.plugins.push(require("tailwindcss"));
          postcssOptions.plugins.push(require("autoprefixer"));
          return postcssOptions;
        },
      };
    },
    [
      require.resolve("@easyops-cn/docusaurus-search-local"),
      /** @type {import("@easyops-cn/docusaurus-search-local").PluginOptions} */
      ({
        // ... Your options.
        // `hashed` is recommended as long-term-cache of index file is possible.
        hashed: true,
        docsRouteBasePath: "/"
        // For Docs using Chinese, The `language` is recommended to set to:
        // ```
        // language: ["en", "zh"],
        // ```
      }),
    ],
  ],

  presets: [
    [
      'classic',
      /** @type {import('@docusaurus/preset-classic').Options} */
      ({
        docs: {
          // routeBasePath: 'touchlab',
          sidebarPath: require.resolve('./sidebars.js'),
          // Please change this to your repo.
          // Remove this to remove the "edit this page" links.
          editUrl:
              `https://github.com/${touchlabConfig.docusaurusConfig.organizationName}/${touchlabConfig.docusaurusConfig.projectName}/tree/main/website/`,
          showLastUpdateTime: true,
          showLastUpdateAuthor: true,
          includeCurrentVersion: true,
          lastVersion: 'current'
        },
        blog: {
          showReadingTime: true,
          // Please change this to your repo.
          // Remove this to remove the "edit this page" links.
          editUrl:
              `https://github.com/${touchlabConfig.docusaurusConfig.organizationName}/${touchlabConfig.docusaurusConfig.projectName}/tree/main/website/`,
        },
        theme: {
          customCss: require.resolve('./src/css/custom.css'),
        },
      }),
    ],
  ],
  markdown: {
    mermaid: true,
  },
  themes: ['@docusaurus/theme-mermaid'],
  themeConfig:

  /** @type {import('@docusaurus/preset-classic').ThemeConfig} */
      ({

        colorMode: {
          defaultMode: 'dark',
        },
        navbar: {
          title: touchlabConfig.docusaurusConfig.title,
          // style: 'dark',
          logo: {
            alt: 'Touchlab Logo',
            src: 'img/Touchlab_Gradient.png',
            href: 'https://touchlab.co'
          },
          items: [
            {
              type: 'docsVersion',
              to: '/docs',
              label: 'Docs',
            },
            {
              type: 'docsVersion',
              to: '/docs/0.3.x',
              label: 'v0.3.x',
            },
            {
              type: 'dropdown',
              label: 'Support',
              position: 'right',
              items: [
                {
                  label: 'Github Issues',
                  href: 'https://github.com/touchlab/KMMBridge/issues',
                },
                  {
                  label: 'KotlinLang Slack',
                  href: 'https://kotlinlang.slack.com/archives/CTJB58X7X',
                },
                  {
                  label: 'Professional Support',
                  href: 'https://touchlab.co/tlpro',
                },

              ],
            },
            {
              href: `https://github.com/${touchlabConfig.docusaurusConfig.organizationName}/${touchlabConfig.docusaurusConfig.projectName}`,
              label: 'GitHub',
              position: 'right',
            },
          ],
        },
        footer: {
          style: 'dark',
          links: [
            {
              title: 'Touchlab',
              items: [
                {
                  label: 'Website',
                  href: 'https://touchlab.co/',
                },
                  {
                  label: 'Newsletter Signup',
                  href: 'https://touchlab.co/oss?s=shownewsletter',
                },
              ],
            },
            {
              title: 'Community',
              items: [
                {
                  label: 'Twitter',
                  href: 'https://twitter.com/TouchlabHQ',
                },
              ],
            },
            {
              title: 'More',
              items: [
                {
                  label: `${touchlabConfig.docusaurusConfig.projectName} GitHub`,
                  href: `https://github.com/${touchlabConfig.docusaurusConfig.organizationName}/${touchlabConfig.docusaurusConfig.projectName}`,
                },
              ],
            },
          ],
          copyright: `Copyright © ${new Date().getFullYear()} Touchlab`,
        },
        prism: {
          // theme: require('./src/utils/DarkTheme').theme,
          theme: lightCodeTheme,//require('prism-react-renderer/themes/nightOwl'),
          darkTheme: darkCodeTheme,
          additionalLanguages: ['kotlin', 'java', 'ruby', 'swift', 'toml'],
        },
        announcementBar: {
          id: 'kmmbridge_old_bar',
          content:
              'These docs are for older versions of KMMBridge. Please go to <a target="_blank" href="https://touchlab.co/kmmbridge/">https://touchlab.co/kmmbridge/</a> for the updated KMMBridge info.',
          isCloseable: true,
        },
      }),
};

module.exports = {
  ...config,
  ...touchlabConfig.docusaurusConfig
};
