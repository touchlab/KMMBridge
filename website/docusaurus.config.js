// @ts-check
// Note: type annotations allow type checking and IDEs autocompletion

const lightCodeTheme = require('prism-react-renderer/themes/github');
const darkCodeTheme = require('prism-react-renderer/themes/dracula');
const simplePlantUML = require("@akebifiky/remark-simple-plantuml");

// const darkTheme = require('prism-react-renderer/themes/vsDark/index.cjs')
// const codeTheme = require('prism-react-renderer/themes/oceanicNext')
// codeTheme.plain.color = "#ff0000"

var colors = {
  function: "#ffc66d",
  comment: "#808080",
  char: "#e8bf6a",
  keyword: "#cc7832",
  boolean: "#cc7832",
  primitive: "#6897bb",
  string: "#6a8759",
  variable: "#a9b7c6",
  variable2: "#9876aa",
  className: "#a9b7c6",
  method: "#ffc66d",

  //Don't know what these should be...
  punctuation: "#c8d0de",
  tag: "#ff0000",
  operator: "#ff0000"
};
var codeTheme = {
  plain: {
    backgroundColor: "#2b2b2b",
    color: "#c8d0de"
  },
  styles: [{
    types: ["attr-name"],
    style: {
      color: colors.keyword
    }
  }, {
    types: ["attr-value"],
    style: {
      color: colors.string
    }
  }, {
    types: ["comment", "block-comment", "prolog", "doctype", "cdata", "shebang"],
    style: {
      color: colors.comment
    }
  }, {
    types: ["property", "number", "function-name", "constant", "symbol", "deleted"],
    style: {
      color: colors.primitive
    }
  }, {
    types: ["boolean"],
    style: {
      color: colors.boolean
    }
  }, {
    types: ["tag"],
    style: {
      color: colors.tag
    }
  }, {
    types: ["string"],
    style: {
      color: colors.string
    }
  }, {
    types: ["punctuation"],
    style: {
      color: colors.punctuation
    }
  }, {
    types: ["selector", "char", "builtin", "inserted"],
    style: {
      color: colors.char
    }
  }, {
    types: ["function"],
    style: {
      color: colors.function
    }
  }, {
    types: ["operator", "entity", "url"],
    style: {
      color: colors.variable
    }
  }, {
    types: ["variable", "property", "constant", "delimiter"],
    style: {
      color: colors.variable2
    }
  }, {
    types: ["keyword"],
    style: {
      color: colors.keyword
    }
  }, {
    types: ["at-rule", "class-name"],
    style: {
      color: colors.className
    }
  }, {
    types: ["important"],
    style: {
      fontWeight: "400"
    }
  }, {
    types: ["bold"],
    style: {
      fontWeight: "bold"
    }
  }, {
    types: ["italic"],
    style: {
      fontStyle: "italic"
    }
  }, {
    types: ["namespace"],
    style: {
      opacity: 0.7
    }
  }]
};


/** @type {import('@docusaurus/types').Config} */
const config = {
  title: 'KMMBridge',
  tagline: 'Kotlin Mobile Multiplatform Packaging',
  url: 'https://touchlab.github.io/KMMBridge/',
  baseUrl: '/KMMBridge/',
  onBrokenLinks: 'throw',
  onBrokenMarkdownLinks: 'warn',
  favicon: 'img/favicon.ico',

  // GitHub pages deployment config.
  // If you aren't using GitHub pages, you don't need these.
  organizationName: 'touchlab', // Usually your GitHub org/user name.
  projectName: 'KMMBridge', // Usually your repo name.

  // Even if you don't use internalization, you can use this field to set useful
  // metadata like html lang. For example, if your site is Chinese, you may want
  // to replace "en" with "zh-Hans".
  i18n: {
    defaultLocale: 'en',
    locales: ['en'],
  },

  // plugins: ['@docusaurus/plugin-content-tldocs'],

  presets: [
    [
      'classic',
      /** @type {import('@docusaurus/preset-classic').Options} */
      ({
        docs: {
          routeBasePath: '/',
          sidebarPath: require.resolve('./sidebars.js'),
          // Please change this to your repo.
          // Remove this to remove the "edit this page" links.
          editUrl:
            'https://github.com/touchlab/KMMBridge/tree/main/website/',
          showLastUpdateTime: true,
          showLastUpdateAuthor: true,
          remarkPlugins: [simplePlantUML]
        },
        blog: false,
        theme: {
          customCss: require.resolve('./src/css/custom.css'),
        },
      }),
    ],
  ],

  themeConfig:
  /** @type {import('@docusaurus/preset-classic').ThemeConfig} */
      ({
        colorMode: {
          defaultMode: 'dark',
        },
        navbar: {
        title: 'KMMBridge',
          // style: 'dark',
          logo: {
            alt: 'Touchlab Logo',
            src: 'img/Touchlab_Gradient.png',
          },
          items: [
            {
              type: 'doc',
              docId: 'intro',
              position: 'left',
              label: 'Docs',
            },
            {
              href: 'https://touchlab.co/',
              label: 'Touchlab Site',
              position: 'right',
            },
            {
              href: 'https://github.com/touchlab/KMMBridge',
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
                  label: 'Touchlab Github',
                  href: 'https://github.com/touchlab/',
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
                  label: 'KMMBridge GitHub',
                  href: 'https://github.com/touchlab/KMMBridge',
                },
              ],
            },
          ],
          copyright: `Copyright © ${new Date().getFullYear()} Touchlab`,
        },
        prism: {
          // theme: require('./src/utils/DarkTheme').theme,
          theme: lightCodeTheme,//require('prism-react-renderer/themes/nightOwl'),
          darkTheme: codeTheme,
          additionalLanguages: ['kotlin', 'java', 'ruby', 'swift', 'toml'],
        },
      }),
};

module.exports = config;
