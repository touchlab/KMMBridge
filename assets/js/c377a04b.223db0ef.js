"use strict";(self.webpackChunktouchlab=self.webpackChunktouchlab||[]).push([[6971],{3905:(e,t,o)=>{o.d(t,{Zo:()=>u,kt:()=>g});var n=o(7294);function i(e,t,o){return t in e?Object.defineProperty(e,t,{value:o,enumerable:!0,configurable:!0,writable:!0}):e[t]=o,e}function a(e,t){var o=Object.keys(e);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(e);t&&(n=n.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),o.push.apply(o,n)}return o}function r(e){for(var t=1;t<arguments.length;t++){var o=null!=arguments[t]?arguments[t]:{};t%2?a(Object(o),!0).forEach((function(t){i(e,t,o[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(o)):a(Object(o)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(o,t))}))}return e}function l(e,t){if(null==e)return{};var o,n,i=function(e,t){if(null==e)return{};var o,n,i={},a=Object.keys(e);for(n=0;n<a.length;n++)o=a[n],t.indexOf(o)>=0||(i[o]=e[o]);return i}(e,t);if(Object.getOwnPropertySymbols){var a=Object.getOwnPropertySymbols(e);for(n=0;n<a.length;n++)o=a[n],t.indexOf(o)>=0||Object.prototype.propertyIsEnumerable.call(e,o)&&(i[o]=e[o])}return i}var s=n.createContext({}),d=function(e){var t=n.useContext(s),o=t;return e&&(o="function"==typeof e?e(t):r(r({},t),e)),o},u=function(e){var t=d(e.components);return n.createElement(s.Provider,{value:t},e.children)},p="mdxType",c={inlineCode:"code",wrapper:function(e){var t=e.children;return n.createElement(n.Fragment,{},t)}},h=n.forwardRef((function(e,t){var o=e.components,i=e.mdxType,a=e.originalType,s=e.parentName,u=l(e,["components","mdxType","originalType","parentName"]),p=d(o),h=i,g=p["".concat(s,".").concat(h)]||p[h]||c[h]||a;return o?n.createElement(g,r(r({ref:t},u),{},{components:o})):n.createElement(g,r({ref:t},u))}));function g(e,t){var o=arguments,i=t&&t.mdxType;if("string"==typeof e||i){var a=o.length,r=new Array(a);r[0]=h;var l={};for(var s in t)hasOwnProperty.call(t,s)&&(l[s]=t[s]);l.originalType=e,l[p]="string"==typeof e?e:i,r[1]=l;for(var d=2;d<a;d++)r[d]=o[d];return n.createElement.apply(null,r)}return n.createElement.apply(null,o)}h.displayName="MDXCreateElement"},1269:(e,t,o)=>{o.r(t),o.d(t,{assets:()=>s,contentTitle:()=>r,default:()=>p,frontMatter:()=>a,metadata:()=>l,toc:()=>d});var n=o(7462),i=(o(7294),o(3905));const a={sidebar_position:1,title:"KMMBridge Intro"},r="KMMBridge for Teams",l={unversionedId:"index",id:"index",title:"KMMBridge Intro",description:"KMMBridge is a set of Gradle tooling that facilitates publishing and consuming pre-built Kotlin Multiplatform Xcode Framework binaries.",source:"@site/docs/index.md",sourceDirName:".",slug:"/",permalink:"/docs/",draft:!1,editUrl:"https://github.com/touchlab/KMMBridge/tree/main/website/docs/index.md",tags:[],version:"current",lastUpdatedBy:"Kevin Galligan",lastUpdatedAt:1725391153,formattedLastUpdatedAt:"Sep 3, 2024",sidebarPosition:1,frontMatter:{sidebar_position:1,title:"KMMBridge Intro"},sidebar:"tutorialSidebar",next:{title:"What Are We Doing?",permalink:"/docs/WHAT_ARE_WE_DOING"}},s={},d=[{value:"Feature Snapshot",id:"feature-snapshot",level:2},{value:"Who is this for?",id:"who-is-this-for",level:2},{value:"KMMBridge Quick Start Updates",id:"kmmbridge-quick-start-updates",level:2},{value:"What Are We Doing?",id:"what-are-we-doing",level:2},{value:"Default GitHub Workflow",id:"default-github-workflow",level:2},{value:"Types of Kotlin Repos",id:"types-of-kotlin-repos",level:2},{value:"Configuration",id:"configuration",level:2},{value:"Detailed Configuration Documentation",id:"detailed-configuration-documentation",level:2},{value:"Local Kotlin Testing",id:"local-kotlin-testing",level:2},{value:"Some notes",id:"some-notes",level:3},{value:"See Also",id:"see-also",level:2}],u={toc:d};function p(e){let{components:t,...o}=e;return(0,i.kt)("wrapper",(0,n.Z)({},u,o,{components:t,mdxType:"MDXLayout"}),(0,i.kt)("h1",{id:"kmmbridge-for-teams"},"KMMBridge for Teams"),(0,i.kt)("p",null,"KMMBridge is a set of Gradle tooling that facilitates publishing and consuming pre-built Kotlin Multiplatform Xcode Framework binaries."),(0,i.kt)("p",null,"The modules can be published to various back ends, public or private, and (currently) consumed by either CocoaPods or Swift Package Manager."),(0,i.kt)("h2",{id:"feature-snapshot"},"Feature Snapshot"),(0,i.kt)("ul",null,(0,i.kt)("li",{parentName:"ul"},"Creates XCFramework zip archives from your Kotlin Xcode Framework modules"),(0,i.kt)("li",{parentName:"ul"},"Publish those archives to various online storage locations"),(0,i.kt)("li",{parentName:"ul"},"Configure and publish versions for Swift Package Manager (SPM) and CocoaPods that can be used by other developers")),(0,i.kt)("p",null,"In addition, KMMBridge provides a fairly basic local development SPM flow, along with it's SPM publishing functionality."),(0,i.kt)("h2",{id:"who-is-this-for"},"Who is this for?"),(0,i.kt)("p",null,"Anybody that needs to publish Xcode Frameworks from Kotlin for use by iOS developers. This can be for teams trying KMP that don't want to disrupt their build setups, larger teams that need to modularize, teams publishing SDKs to internal or external clients."),(0,i.kt)("p",null,"Anybody that needs to publish a Kotlin Xcode Framework."),(0,i.kt)("genericCta",{message:"We build solutions that get teams started smoothly with Kotlin Multiplatform Mobile and ensure their success in production. Join our community to learn how your peers are adopting KMM.",link:"https://touchlab.co/?s=shownewsletter",buttonMessage:"Subscribe!"}),(0,i.kt)("h2",{id:"kmmbridge-quick-start-updates"},(0,i.kt)("a",{parentName:"h2",href:"https://touchlab.co/kmmbridge-quick-start"},"KMMBridge Quick Start Updates")),(0,i.kt)("p",null,"This is a post series that explains the basics, and provides a template GitHub project that you can start using right away. If you want to test out sharing KMP libraries with your team, this is the fastest way to get going."),(0,i.kt)("h2",{id:"what-are-we-doing"},(0,i.kt)("a",{parentName:"h2",href:"/docs/WHAT_ARE_WE_DOING"},"What Are We Doing?")),(0,i.kt)("p",null,"It is a bit difficult to describe how to set up and deploy KMMBridge without going through the details of exactly what needs to happen to publish Xcode Framework binaries. This section walks through the parts you need to understand to figure out what KMMBridge is doing and why certains pieces work they way they do."),(0,i.kt)("h2",{id:"default-github-workflow"},(0,i.kt)("a",{parentName:"h2",href:"/docs/DEFAULT_GITHUB_FLOW"},"Default GitHub Workflow")),(0,i.kt)("p",null,"KMMBridge is designed to live in CI and your build pipeline. There is information that needs to be passed into KMMBridge, outside configuration and access needs to be granted, git operations that need to happen. There's a lot happening outside of KMMBridge itself, and the details of that configureation depend on your build environment, code and artifact hosting, etc."),(0,i.kt)("p",null,"Having off-the-shelf config for every possible scenario would be a challenge, and in real world production environments, we've found that there's always a lot of customization anyway."),(0,i.kt)("p",null,"We do provide a relatively off-the-shelf experience for repos in GitHub, using GitHub Actions and GitHub Packages. The ",(0,i.kt)("a",{parentName:"p",href:"https://touchlab.co/kmmbridge-quick-start"},"KMMBridge Quick Start Updates")," post and template project use this setup."),(0,i.kt)("p",null,"The ",(0,i.kt)("a",{parentName:"p",href:"/docs/DEFAULT_GITHUB_FLOW"},"Default GitHub Workflow")," will walk through the parts of the default GitHub flow. If you need to customize a KMMBridge build, this is where you should look."),(0,i.kt)("admonition",{type:"info"},(0,i.kt)("p",{parentName:"admonition"},"The first version of KMMBridge, 0.3.x, attempted to put a lot of the CI logic inside the Gradle plugin itself, but this added to the complexity and reduced flexibility. Version 0.5+ is more streamlined. If you are porting your existing KMMBridge project, see ",(0,i.kt)("a",{parentName:"p",href:"PORTING_0.3.x"},"Porting from KMMBridge 0.3.x")," for an overview of what's changed and how to approach porting your build.")),(0,i.kt)("admonition",{type:"caution"},(0,i.kt)("p",{parentName:"admonition"},"If you were using the original version of KMMBridge, the updated version has a different plugin id."),(0,i.kt)("pre",{parentName:"admonition"},(0,i.kt)("code",{parentName:"pre",className:"language-kotlin"},'id("co.touchlab.kmmbridge")\n'))),(0,i.kt)("h2",{id:"types-of-kotlin-repos"},"Types of Kotlin Repos"),(0,i.kt)("p",null,"Teams using KMP have a variety of repo configurations. They include"),(0,i.kt)("ul",null,(0,i.kt)("li",{parentName:"ul"},"Monorepo"),(0,i.kt)("li",{parentName:"ul"},"Android/Kotlin with a shared KMP module (or modules)"),(0,i.kt)("li",{parentName:"ul"},"Separate repo with the shared KMP that is published to both iOS and Android")),(0,i.kt)("p",null,"KMMBridge can be used in any of these repo configurations, depending on your needs."),(0,i.kt)("h2",{id:"configuration"},"Configuration"),(0,i.kt)("p",null,"The plugin is currently published to the maven central repo. If needed, makes sure to add the ",(0,i.kt)("inlineCode",{parentName:"p"},"mavenCentral()")," repo to ",(0,i.kt)("inlineCode",{parentName:"p"},"pluginManagement")," or the ",(0,i.kt)("inlineCode",{parentName:"p"},"buildscript")," block:"),(0,i.kt)("pre",null,(0,i.kt)("code",{parentName:"pre",className:"language-kotlin"},"pluginManagement {\n    repositories {\n        gradlePluginPortal()\n        mavenCentral()\n    }\n}\n")),(0,i.kt)("p",null,"Then add the plugin to the module that is actually building the Xcode Framework. In the ",(0,i.kt)("inlineCode",{parentName:"p"},"build.gradle.kts")," file:"),(0,i.kt)("pre",null,(0,i.kt)("code",{parentName:"pre",className:"language-kotlin"},'plugins {\n    kotlin("multiplatform")\n    id("co.touchlab.kmmbridge") version "1.0.0"\n}\n')),(0,i.kt)("p",null,"At the top level in the same file, put the ",(0,i.kt)("inlineCode",{parentName:"p"},"kmmbridge")," configuration:"),(0,i.kt)("pre",null,(0,i.kt)("code",{parentName:"pre",className:"language-kotlin"},'kmmbridge {\n    mavenPublishArtifacts()\n    spm()\n    cocoapods("git@github.com:touchlab/PublicPodSpecs.git")\n    //etc\n}\n')),(0,i.kt)("h2",{id:"detailed-configuration-documentation"},"Detailed Configuration Documentation"),(0,i.kt)("p",null,"For non-GitHub installations, other artifact locations, etc, see ",(0,i.kt)("a",{parentName:"p",href:"/docs/general/CONFIGURATION_OVERVIEW"},"CONFIGURATION_OVERVIEW"),"."),(0,i.kt)("h2",{id:"local-kotlin-testing"},"Local Kotlin Testing"),(0,i.kt)("p",null,"Many teams, especially for existing apps, introduce KMP to their environment by publishing an Xcode Framework that their iOS dev team consumes like any other dependency. That makes integration easy, but creates several problems with development, testing, and simply getting the iOS team to start editing the shared code."),(0,i.kt)("p",null,"While introducing KMP this way is often the best option, we feel strongly that being able to locally build and test the shared Kotlin directly in the target apps is a critical feature."),(0,i.kt)("p",null,"KMMBridge provides support for locally editing the published Kotlin. More info here: ",(0,i.kt)("a",{parentName:"p",href:"/docs/cocoapods/IOS_LOCAL_DEV_COCOAPODS"},"CocoaPods")," and ",(0,i.kt)("a",{parentName:"p",href:"/docs/spm/IOS_LOCAL_DEV_SPM"},"SPM"),"."),(0,i.kt)("h3",{id:"some-notes"},"Some notes"),(0,i.kt)("ul",null,(0,i.kt)("li",{parentName:"ul"},"Groovy was not a focus during dev. All testing effort has been with Kotlin Gradle scripts. See ",(0,i.kt)("a",{parentName:"li",href:"/docs/general/GROOVY_BUILD_SCRIPTS"},"GROOVY_BUILD_SCRIPTS")," for suggestions and samples if needed.")),(0,i.kt)("h2",{id:"see-also"},"See Also"),(0,i.kt)("p",null,(0,i.kt)("a",{parentName:"p",href:"/docs/TROUBLESHOOTING"},"TROUBLESHOOTING")))}p.isMDXComponent=!0}}]);