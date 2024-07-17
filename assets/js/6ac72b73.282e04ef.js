"use strict";(self.webpackChunktouchlab=self.webpackChunktouchlab||[]).push([[5395],{3905:(e,t,o)=>{o.d(t,{Zo:()=>p,kt:()=>h});var n=o(7294);function a(e,t,o){return t in e?Object.defineProperty(e,t,{value:o,enumerable:!0,configurable:!0,writable:!0}):e[t]=o,e}function i(e,t){var o=Object.keys(e);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(e);t&&(n=n.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),o.push.apply(o,n)}return o}function r(e){for(var t=1;t<arguments.length;t++){var o=null!=arguments[t]?arguments[t]:{};t%2?i(Object(o),!0).forEach((function(t){a(e,t,o[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(o)):i(Object(o)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(o,t))}))}return e}function l(e,t){if(null==e)return{};var o,n,a=function(e,t){if(null==e)return{};var o,n,a={},i=Object.keys(e);for(n=0;n<i.length;n++)o=i[n],t.indexOf(o)>=0||(a[o]=e[o]);return a}(e,t);if(Object.getOwnPropertySymbols){var i=Object.getOwnPropertySymbols(e);for(n=0;n<i.length;n++)o=i[n],t.indexOf(o)>=0||Object.prototype.propertyIsEnumerable.call(e,o)&&(a[o]=e[o])}return a}var s=n.createContext({}),u=function(e){var t=n.useContext(s),o=t;return e&&(o="function"==typeof e?e(t):r(r({},t),e)),o},p=function(e){var t=u(e.components);return n.createElement(s.Provider,{value:t},e.children)},d="mdxType",c={inlineCode:"code",wrapper:function(e){var t=e.children;return n.createElement(n.Fragment,{},t)}},m=n.forwardRef((function(e,t){var o=e.components,a=e.mdxType,i=e.originalType,s=e.parentName,p=l(e,["components","mdxType","originalType","parentName"]),d=u(o),m=a,h=d["".concat(s,".").concat(m)]||d[m]||c[m]||i;return o?n.createElement(h,r(r({ref:t},p),{},{components:o})):n.createElement(h,r({ref:t},p))}));function h(e,t){var o=arguments,a=t&&t.mdxType;if("string"==typeof e||a){var i=o.length,r=new Array(i);r[0]=m;var l={};for(var s in t)hasOwnProperty.call(t,s)&&(l[s]=t[s]);l.originalType=e,l[d]="string"==typeof e?e:a,r[1]=l;for(var u=2;u<i;u++)r[u]=o[u];return n.createElement.apply(null,r)}return n.createElement.apply(null,o)}m.displayName="MDXCreateElement"},2511:(e,t,o)=>{o.r(t),o.d(t,{assets:()=>s,contentTitle:()=>r,default:()=>d,frontMatter:()=>i,metadata:()=>l,toc:()=>u});var n=o(7462),a=(o(7294),o(3905));const i={sidebar_position:1,title:"KMMBridge Intro"},r="KMMBridge for Teams",l={unversionedId:"index",id:"version-0.3.x/index",title:"KMMBridge Intro",description:"KMMBridge is a set of Gradle tooling that facilitates publishing and consuming pre-built KMM (Kotlin Multiplatform Mobile) Xcode Framework binaries.",source:"@site/versioned_docs/version-0.3.x/index.md",sourceDirName:".",slug:"/",permalink:"/docs/0.3.x/",draft:!1,editUrl:"https://github.com/touchlab/KMMBridge/tree/main/website/versioned_docs/version-0.3.x/index.md",tags:[],version:"0.3.x",lastUpdatedBy:"Kevin Galligan",lastUpdatedAt:1721226729,formattedLastUpdatedAt:"Jul 17, 2024",sidebarPosition:1,frontMatter:{sidebar_position:1,title:"KMMBridge Intro"},sidebar:"tutorialSidebar",next:{title:"Default GitHub Workflow",permalink:"/docs/0.3.x/DEFAULT_GITHUB_FLOW"}},s={},u=[{value:"Who is this for?",id:"who-is-this-for",level:2},{value:"Simple Getting Started Setup",id:"simple-getting-started-setup",level:2},{value:"KMMBridge Kick Start",id:"kmmbridge-kick-start",level:2},{value:"Sample Projects",id:"sample-projects",level:2},{value:"Basic Flow",id:"basic-flow",level:2},{value:"Configuration",id:"configuration",level:2},{value:"Detailed Configuration Documentation",id:"detailed-configuration-documentation",level:2},{value:"Local Kotlin Testing",id:"local-kotlin-testing",level:2},{value:"Project Status",id:"project-status",level:2},{value:"Some notes",id:"some-notes",level:3},{value:"See Also",id:"see-also",level:2}],p={toc:u};function d(e){let{components:t,...o}=e;return(0,a.kt)("wrapper",(0,n.Z)({},p,o,{components:t,mdxType:"MDXLayout"}),(0,a.kt)("h1",{id:"kmmbridge-for-teams"},"KMMBridge for Teams"),(0,a.kt)("p",null,"KMMBridge is a set of Gradle tooling that facilitates publishing and consuming pre-built KMM (Kotlin Multiplatform Mobile) Xcode Framework binaries."),(0,a.kt)("p",null,"The modules can be published to various back ends, public or private, and (currently) consumed by either CocoaPods or Swift Package Manager."),(0,a.kt)("h2",{id:"who-is-this-for"},"Who is this for?"),(0,a.kt)("p",null,"Different types of teams and different types of projects use Kotlin Multiplatform in different ways. Native mobile dev teams often want to start by including a prebuilt Xcode Framework in the iOS build rather than having every member of the team building Kotlin locally. This is especially true when adding KMM to an existing app, and/or when the teams are larger than a few developers."),(0,a.kt)("p",null,'Building and publishing binary Xcode Frameworks from Kotlin is certainly possible, but not easily supported "out of the box". Where those binaries are published, and how they are included in the iOS build, also varies. Most teams we have talked to go through the same process getting started. They first need to build some kind of publishing architecture, which is non-trivial, and make a lot of the same mistakes along the way.'),(0,a.kt)("p",null,"For more context, see Nate Ebel\u2019s talk from Droidcon NYC 2022: ",(0,a.kt)("a",{parentName:"p",href:"https://www.droidcon.com/2022/09/29/adopting-kotlin-multiplatform-in-brownfield-applications/"},"Adopting Kotlin Multiplatform in Brownfield Applications"),". It's a very good overview of the startup issues teams face."),(0,a.kt)("genericCta",{message:"We build solutions that get teams started smoothly with Kotlin Multiplatform Mobile and ensure their success in production. Join our community to learn how your peers are adopting KMM.",link:"https://touchlab.co/?s=shownewsletter",buttonMessage:"Subscribe!"}),(0,a.kt)("h2",{id:"simple-getting-started-setup"},"Simple Getting Started Setup"),(0,a.kt)("p",null,"If you are using GitHub for source control, and are OK with using GitHub Actions to build and GitHub releases for published artifacts, we have a simple setup flow you can use. It is the easiest default to start from."),(0,a.kt)("p",null,"See ",(0,a.kt)("a",{parentName:"p",href:"/docs/0.3.x/DEFAULT_GITHUB_FLOW"},"DEFAULT_GITHUB_FLOW")," for setup instructions."),(0,a.kt)("h2",{id:"kmmbridge-kick-start"},"KMMBridge Kick Start"),(0,a.kt)("p",null,'The quickest way to get up and running is to use our template "Kick Start" project. See ',(0,a.kt)("a",{parentName:"p",href:"https://github.com/touchlab/KMMBridgeKickStart"},"KMMBridgeKickStart")),(0,a.kt)("h2",{id:"sample-projects"},"Sample Projects"),(0,a.kt)("ul",null,(0,a.kt)("li",{parentName:"ul"},(0,a.kt)("a",{parentName:"li",href:"https://github.com/touchlab/KMMBridgeSampleKotlin"},"KMMBridgeSampleKotlin")," - Shared Kotlin code"),(0,a.kt)("li",{parentName:"ul"},(0,a.kt)("a",{parentName:"li",href:"https://github.com/touchlab/KMMBridgeSampleSpm"},"KMMBridgeSampleSpm")," - Xcode example with SPM"),(0,a.kt)("li",{parentName:"ul"},(0,a.kt)("a",{parentName:"li",href:"https://github.com/touchlab/KMMBridgeSampleCocoaPods"},"KMMBridgeSampleCocoaPods")," - Xcode example with CocoaPods"),(0,a.kt)("li",{parentName:"ul"},(0,a.kt)("a",{parentName:"li",href:"https://github.com/touchlab/PublicPodspecs"},"PublicPodspecs")," - Public CocoaPods podspec repo")),(0,a.kt)("h2",{id:"basic-flow"},"Basic Flow"),(0,a.kt)("p",null,"The basic concept is that after making some changes to Kotlin code, you'll want to publish an updated iOS Framework that Xcode can grab and use. Most native mobile projects exist as 2 separate repos: one for Android and one for iOS. To add some shared Kotlin code, you can either add a KMM module to the Android project, or create a separte repo just for the shared Kotlin code. In either configuration, you publish the iOS Framework and integrate it into the Xcode project."),(0,a.kt)("p",null,"Changes are made and tested to the shared Kotlin, then pushed to source control. When that happens, you can run CI to publish a new build. Doing that will:"),(0,a.kt)("ul",null,(0,a.kt)("li",{parentName:"ul"},"Create a new version number"),(0,a.kt)("li",{parentName:"ul"},"Publish the Xcode Framework zip"),(0,a.kt)("li",{parentName:"ul"},"Generate ",(0,a.kt)("inlineCode",{parentName:"li"},"Package.swift")," file and/or a CocoaPods podspec file")),(0,a.kt)("p",null,"The iOS app can then include these frameworks through SPM or CocoaPods."),(0,a.kt)("p",null,(0,a.kt)("img",{parentName:"p",src:"https://tl-navigator-images.s3.us-east-1.amazonaws.com/docimages/2022-10-07_09-13-kmmbridge_diagram2.png",alt:"kmmbridge_diagram2"})),(0,a.kt)("h2",{id:"configuration"},"Configuration"),(0,a.kt)("p",null,"The plugin is currently published to the maven central repo. If needed, makes sure to add the ",(0,a.kt)("inlineCode",{parentName:"p"},"mavenCentral()")," repo to ",(0,a.kt)("inlineCode",{parentName:"p"},"pluginManagement")," or the ",(0,a.kt)("inlineCode",{parentName:"p"},"buildscript")," block:"),(0,a.kt)("pre",null,(0,a.kt)("code",{parentName:"pre",className:"language-kotlin"},"pluginManagement {\n    repositories {\n        gradlePluginPortal()\n        mavenCentral()\n    }\n}\n")),(0,a.kt)("p",null,"Note: If you're using a SNAPSHOT version of the plugin, add the SNAPSHOT repo as well:"),(0,a.kt)("pre",null,(0,a.kt)("code",{parentName:"pre",className:"language-kotlin"},'pluginManagement {\n    repositories {\n        gradlePluginPortal()\n        mavenCentral()\n        maven("https://oss.sonatype.org/content/repositories/snapshots")\n    }\n}\n')),(0,a.kt)("p",null,"Then add the plugin to the module that is actually building the Xcode Framework. In the ",(0,a.kt)("inlineCode",{parentName:"p"},"build.gradle.kts")," file:"),(0,a.kt)("pre",null,(0,a.kt)("code",{parentName:"pre",className:"language-kotlin"},'plugins {\n    kotlin("multiplatform")\n    id("co.touchlab.faktory.kmmbridge") version "{{VERSION_NAME_3X}}"\n}\n')),(0,a.kt)("p",null,"At the top level in the same file, put the ",(0,a.kt)("inlineCode",{parentName:"p"},"kmmbridge")," configuration:"),(0,a.kt)("pre",null,(0,a.kt)("code",{parentName:"pre",className:"language-kotlin"},'kmmbridge {\n    mavenPublishArtifacts()\n    githubReleaseVersions()\n    spm()\n    cocoapods("git@github.com:touchlab/PublicPodSpecs.git")\n    versionPrefix.set("0.3")\n    //etc\n}\n')),(0,a.kt)("h2",{id:"detailed-configuration-documentation"},"Detailed Configuration Documentation"),(0,a.kt)("p",null,"For non-GitHub installations, other artifact locations, etc, see ",(0,a.kt)("a",{parentName:"p",href:"/docs/0.3.x/general/CONFIGURATION_OVERVIEW"},"CONFIGURATION_OVERVIEW"),"."),(0,a.kt)("h2",{id:"local-kotlin-testing"},"Local Kotlin Testing"),(0,a.kt)("p",null,'KMMBridge also provides some support for locally building and testing Kotlin-generated Frameworks directly in your Xcode project. You can "flip a switch" to run your Xcode project against Kotlin locally, to test your changes. This process differs depending on if you\'re using ',(0,a.kt)("a",{parentName:"p",href:"/docs/0.3.x/cocoapods/IOS_LOCAL_DEV_COCOAPODS"},"CocoaPods")," and ",(0,a.kt)("a",{parentName:"p",href:"/docs/0.3.x/spm/IOS_LOCAL_DEV_SPM"},"SPM"),"."),(0,a.kt)("h2",{id:"project-status"},"Project Status"),(0,a.kt)("p",null,"This project is new. The code was extracted from a longer running internal effort, which went through a lot of experimentation\nand code written for specific use cases. Please let us know if you run into issues or find setup confusing."),(0,a.kt)("h3",{id:"some-notes"},"Some notes"),(0,a.kt)("ul",null,(0,a.kt)("li",{parentName:"ul"},"Groovy was not a focus during dev. All testing effort has been with Kotlin Gradle scripts. See ",(0,a.kt)("a",{parentName:"li",href:"/docs/0.3.x/general/GROOVY_BUILD_SCRIPTS"},"GROOVY_BUILD_SCRIPTS")," for suggestions and samples if needed.")),(0,a.kt)("h2",{id:"see-also"},"See Also"),(0,a.kt)("p",null,(0,a.kt)("a",{parentName:"p",href:"/docs/0.3.x/TROUBLESHOOTING"},"TROUBLESHOOTING")))}d.isMDXComponent=!0}}]);