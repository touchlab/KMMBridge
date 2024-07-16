"use strict";(self.webpackChunktouchlab=self.webpackChunktouchlab||[]).push([[422],{3905:(e,t,a)=>{a.d(t,{Zo:()=>u,kt:()=>h});var n=a(7294);function r(e,t,a){return t in e?Object.defineProperty(e,t,{value:a,enumerable:!0,configurable:!0,writable:!0}):e[t]=a,e}function o(e,t){var a=Object.keys(e);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(e);t&&(n=n.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),a.push.apply(a,n)}return a}function i(e){for(var t=1;t<arguments.length;t++){var a=null!=arguments[t]?arguments[t]:{};t%2?o(Object(a),!0).forEach((function(t){r(e,t,a[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(a)):o(Object(a)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(a,t))}))}return e}function l(e,t){if(null==e)return{};var a,n,r=function(e,t){if(null==e)return{};var a,n,r={},o=Object.keys(e);for(n=0;n<o.length;n++)a=o[n],t.indexOf(a)>=0||(r[a]=e[a]);return r}(e,t);if(Object.getOwnPropertySymbols){var o=Object.getOwnPropertySymbols(e);for(n=0;n<o.length;n++)a=o[n],t.indexOf(a)>=0||Object.prototype.propertyIsEnumerable.call(e,a)&&(r[a]=e[a])}return r}var s=n.createContext({}),p=function(e){var t=n.useContext(s),a=t;return e&&(a="function"==typeof e?e(t):i(i({},t),e)),a},u=function(e){var t=p(e.components);return n.createElement(s.Provider,{value:t},e.children)},d="mdxType",c={inlineCode:"code",wrapper:function(e){var t=e.children;return n.createElement(n.Fragment,{},t)}},m=n.forwardRef((function(e,t){var a=e.components,r=e.mdxType,o=e.originalType,s=e.parentName,u=l(e,["components","mdxType","originalType","parentName"]),d=p(a),m=r,h=d["".concat(s,".").concat(m)]||d[m]||c[m]||o;return a?n.createElement(h,i(i({ref:t},u),{},{components:a})):n.createElement(h,i({ref:t},u))}));function h(e,t){var a=arguments,r=t&&t.mdxType;if("string"==typeof e||r){var o=a.length,i=new Array(o);i[0]=m;var l={};for(var s in t)hasOwnProperty.call(t,s)&&(l[s]=t[s]);l.originalType=e,l[d]="string"==typeof e?e:r,i[1]=l;for(var p=2;p<o;p++)i[p]=a[p];return n.createElement.apply(null,i)}return n.createElement.apply(null,a)}m.displayName="MDXCreateElement"},4735:(e,t,a)=>{a.r(t),a.d(t,{assets:()=>s,contentTitle:()=>i,default:()=>d,frontMatter:()=>o,metadata:()=>l,toc:()=>p});var n=a(7462),r=(a(7294),a(3905));const o={sidebar_position:1},i="Configuration Overview",l={unversionedId:"general/CONFIGURATION_OVERVIEW",id:"general/CONFIGURATION_OVERVIEW",title:"Configuration Overview",description:"Workflow Configuration",source:"@site/docs/general/CONFIGURATION_OVERVIEW.md",sourceDirName:"general",slug:"/general/CONFIGURATION_OVERVIEW",permalink:"/docs/general/CONFIGURATION_OVERVIEW",draft:!1,editUrl:"https://github.com/touchlab/KMMBridge/tree/main/website/docs/general/CONFIGURATION_OVERVIEW.md",tags:[],version:"current",lastUpdatedBy:"Sam Hill",lastUpdatedAt:1721139842,formattedLastUpdatedAt:"Jul 16, 2024",sidebarPosition:1,frontMatter:{sidebar_position:1},sidebar:"tutorialSidebar",previous:{title:"General Documentation",permalink:"/docs/category/general-documentation"},next:{title:"Groovy Build Scripts",permalink:"/docs/general/GROOVY_BUILD_SCRIPTS"}},s={},p=[{value:"Workflow Configuration",id:"workflow-configuration",level:2},{value:"KMMBridge Block",id:"kmmbridge-block",level:3},{value:"VersionManager",id:"versionmanager",level:3},{value:"GitHub Packages",id:"github-packages",level:3},{value:"Optional Gradle Parameters",id:"optional-gradle-parameters",level:3},{value:"Artifact Managers",id:"artifact-managers",level:2},{value:"Maven Repository Artifacts",id:"maven-repository-artifacts",level:3},{value:"S3 Public Artifacts",id:"s3-public-artifacts",level:3},{value:"Dependency Managers",id:"dependency-managers",level:2},{value:"Naming",id:"naming",level:2}],u={toc:p};function d(e){let{components:t,...a}=e;return(0,r.kt)("wrapper",(0,n.Z)({},u,a,{components:t,mdxType:"MDXLayout"}),(0,r.kt)("h1",{id:"configuration-overview"},"Configuration Overview"),(0,r.kt)("h2",{id:"workflow-configuration"},"Workflow Configuration"),(0,r.kt)("h3",{id:"kmmbridge-block"},"KMMBridge Block"),(0,r.kt)("p",null,"In the Gradle build file for your module that exports Xcode Frameworks, you'll apply the KMMBridge Gradle plugin, and add a ",(0,r.kt)("inlineCode",{parentName:"p"},"kmmbridge")," config block."),(0,r.kt)("pre",null,(0,r.kt)("code",{parentName:"pre",className:"language-kotlin"},'id("co.touchlab.kmmbridge")\n\n// Etc\n\nkmmbridge {\n    \n}\n')),(0,r.kt)("p",null,"All of the block config ultimately ends up in the ",(0,r.kt)("a",{parentName:"p",href:"https://github.com/touchlab/KMMBridge/blob/main/kmmbridge/src/main/kotlin/KmmBridgeExtension.kt#L40"},"KMMBridgeExtension"),". "),(0,r.kt)("p",null,"Every config should have an ",(0,r.kt)("a",{parentName:"p",href:"#artifact-managers"},(0,r.kt)("inlineCode",{parentName:"a"},"ArtifactManager"))," and at least one ",(0,r.kt)("a",{parentName:"p",href:"#dependency-managers"},(0,r.kt)("inlineCode",{parentName:"a"},"DependencyManager")),"."),(0,r.kt)("p",null,"You can control the ",(0,r.kt)("a",{parentName:"p",href:"#naming"},"Framework name")," here as well."),(0,r.kt)("p",null,'By default, the "build type" is Release. To change it to Debug, do the following:'),(0,r.kt)("pre",null,(0,r.kt)("code",{parentName:"pre",className:"language-kotlin"},"import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType\n\n// Etc\n\nkmmbridge {\n    buildType.set(NativeBuildType.DEBUG)\n}\n")),(0,r.kt)("h3",{id:"versionmanager"},"VersionManager"),(0,r.kt)("p",null,"Version 0.3.x of KMMBridge had the concept of ",(0,r.kt)("inlineCode",{parentName:"p"},"VersionManager"),", which managed the version for published Xcode Frameworks. The responsibility for versioning has been moved out of KMMBridge, but you can still override the default by setting a custom ",(0,r.kt)("inlineCode",{parentName:"p"},"VersionManager"),". There aren't many reasons you'd want to do this. The most likely scenario would be:"),(0,r.kt)("ul",null,(0,r.kt)("li",{parentName:"ul"},"You are only publishing iOS builds"),(0,r.kt)("li",{parentName:"ul"},"You don't want to set the Gradle version dynamically, or you don't want to use the Gradle version property at all")),(0,r.kt)("p",null,"To use something other than the ",(0,r.kt)("inlineCode",{parentName:"p"},"version")," property, see ",(0,r.kt)("inlineCode",{parentName:"p"},"ManualVersionManager")," and write a custom implementation."),(0,r.kt)("p",null,"If you only want to publish iOS builds and would like KMMBridge to do simple version incrementing, we have a ",(0,r.kt)("inlineCode",{parentName:"p"},"TimestampVersionManager")," instance that simply appends the current timestamp to Gradle's version property. This assumes the Gradle version property will be the major and minor values of a semantic version (ex. ",(0,r.kt)("inlineCode",{parentName:"p"},"2.3"),"). So, a build might have ",(0,r.kt)("inlineCode",{parentName:"p"},"2.3.1695492019324")," for its version."),(0,r.kt)("pre",null,(0,r.kt)("code",{parentName:"pre",className:"language-kotlin"},'version = "2.3"\n\nkmmbridge {\n    timestampVersions()\n}\n')),(0,r.kt)("h3",{id:"github-packages"},"GitHub Packages"),(0,r.kt)("p",null,"There is a special function that handles GitHub Packages repo setup automatically."),(0,r.kt)("pre",null,(0,r.kt)("code",{parentName:"pre",className:"language-kotlin"},"addGithubPackagesRepository()\n")),(0,r.kt)("p",null,"This function reads values provided to CI from our ",(0,r.kt)("a",{parentName:"p",href:"/docs/DEFAULT_GITHUB_FLOW"},"GitHub Actions Workflow"),", and sets up access to publish to GitHub Packages automatically."),(0,r.kt)("h3",{id:"optional-gradle-parameters"},"Optional Gradle Parameters"),(0,r.kt)("p",null,"For local development, KMMBridge configures XCFrameworks and, if you're using SPM, the SPM local dev flow. Publishing a build is really intended to happen from CI, using a predefined script. It can be manually or locally configured, but there are parameters you should be aware of."),(0,r.kt)("p",null,"Generally speaking, you should refer to the ",(0,r.kt)("a",{parentName:"p",href:"/docs/DEFAULT_GITHUB_FLOW"},"Default GitHub Workflow")," for an up-to-date example with everything you'll need."),(0,r.kt)("p",null,"These are some of the parameters you should be aware of:"),(0,r.kt)("p",null,(0,r.kt)("inlineCode",{parentName:"p"},"GITHUB_PUBLISH_TOKEN")," - Gradle parameter. Used on CI with the default workflow to configure auth for validating packages."),(0,r.kt)("p",null,(0,r.kt)("inlineCode",{parentName:"p"},"GITHUB_REPO")," - Gradle parameter. Used on CI with the default workflow to configure auth for validating packages."),(0,r.kt)("p",null,(0,r.kt)("inlineCode",{parentName:"p"},"ENABLE_PUBLISHING")," - Gradle parameter. KMMBridge does some extra setup that isn't necessary if you aren't publishing. This setup may cause warnings, so disabling that part of the Gradle setup may be useful. Add the following to ",(0,r.kt)("inlineCode",{parentName:"p"},"gradle.properties")),(0,r.kt)("pre",null,(0,r.kt)("code",{parentName:"pre"},"ENABLE_PUBLISHING=false\n")),(0,r.kt)("p",null,"In CI, you can override that value with the following."),(0,r.kt)("pre",null,(0,r.kt)("code",{parentName:"pre",className:"language-shell"},"./gradelew -PENABLE_PUBLISHING=true [your tasks]\n")),(0,r.kt)("p",null,(0,r.kt)("strong",{parentName:"p"},"Note:")," Earlier versions of KMMBridge required this parameter, as we were doing git operations locally. The majority of those operations now live outside of the plugin. We do one call to get the repo folder root, and fall back with a warning if there is no git repo. Just FYI."),(0,r.kt)("h2",{id:"artifact-managers"},"Artifact Managers"),(0,r.kt)("p",null,"Artifact Managers handle uploading the binary and generate the url that will be used to access the binary. These implementations are very specific to the back end hosting being used. Current implementations:"),(0,r.kt)("h3",{id:"maven-repository-artifacts"},(0,r.kt)("a",{parentName:"h3",href:"../artifacts/MAVEN_REPO_ARTIFACTS"},"Maven Repository Artifacts")),(0,r.kt)("p",null,"This is the simplest to configure and currently the recommended best way to publish your binaries. If you are using GitHub, you can publish to Github Packages easily with our integration tools. For a complete introduction and overview of how to configure your project with this manager, see ",(0,r.kt)("a",{parentName:"p",href:"/docs/DEFAULT_GITHUB_FLOW"},"Default GitHub WorkFlow"),"."),(0,r.kt)("h3",{id:"s3-public-artifacts"},(0,r.kt)("a",{parentName:"h3",href:"/docs/artifacts/S3_PUBLIC_ARTIFACTS"},"S3 Public Artifacts")),(0,r.kt)("p",null,"This implementation will publish to your S3 bucket. By default it will set the access to public. You can also have access be private and controlled by AWS access, but there is no easy way to give Xcode access to your binaries. You'll need to configure machine access to these buckets (this is more common in larger enterprise environment)."),(0,r.kt)("p",null,"The S3 artifact manager is really the starting point for teams that need a more custom implementation (Azure, Google Cloud, private hosting, etc)."),(0,r.kt)("h2",{id:"dependency-managers"},"Dependency Managers"),(0,r.kt)("p",null,"Dependency managers handle integration with CocoaPods and SPM. They manage generating the config files (podspec or Package.swift), and the publishing of the releases. There are currently only two implementations:"),(0,r.kt)("ul",null,(0,r.kt)("li",{parentName:"ul"},"SpmDependencyManager: ",(0,r.kt)("a",{parentName:"li",href:"/docs/spm/IOS_SPM"},"IOS_SPM")),(0,r.kt)("li",{parentName:"ul"},"CocoapodsDependencyManager: ",(0,r.kt)("a",{parentName:"li",href:"/docs/cocoapods/IOS_COCOAPODS"},"IOS_COCOAPODS"))),(0,r.kt)("h2",{id:"naming"},"Naming"),(0,r.kt)("p",null,"In Kotlin code you can set the name of your Framework as well as the name of your Podfile (when using cocoapods)."),(0,r.kt)("p",null,(0,r.kt)("strong",{parentName:"p"},"Framework base name")," controls the name that will eventually be used in the Swift ",(0,r.kt)("inlineCode",{parentName:"p"},"import")," statement."),(0,r.kt)("p",null,"When using Cocoapods:"),(0,r.kt)("pre",null,(0,r.kt)("code",{parentName:"pre",className:"language-kotlin"},'kotlin {\n    cocoapods {\n        framework {\n            baseName = "FRAMEWORKNAME"\n        }\n    }\n}\n')),(0,r.kt)("p",null,"When using SPM you can set the framework name in the ",(0,r.kt)("inlineCode",{parentName:"p"},"kmmbridge")," block:"),(0,r.kt)("pre",null,(0,r.kt)("code",{parentName:"pre",className:"language-kotlin"},'kmmbridge {\n    frameworkName.set("FRAMEWORKNAME")\n}\n')),(0,r.kt)("p",null,"Cocoapods only:\n",(0,r.kt)("strong",{parentName:"p"},"Podfile name")," controls the name that will eventually be used in the iOS Podfile, and is the name of the podspec file. This is written to the podspec in the ",(0,r.kt)("inlineCode",{parentName:"p"},"spec.name")," field."),(0,r.kt)("pre",null,(0,r.kt)("code",{parentName:"pre",className:"language-kotlin"},'kotlin {\n    cocoapods {\n        name = "PODNAME"\n    }\n}\n')),(0,r.kt)("p",null,"The podspec is uploaded to a folder in the podspec repo based on the KMMBridge version and the Podfile name. Therefore, the path looks like this:"),(0,r.kt)("pre",null,(0,r.kt)("code",{parentName:"pre"},"<podspec-repo-url>/<podname>/<kmm-version>/<podname>.podspec\n")),(0,r.kt)("p",null,"There is a danger of having naming conflicts. If two projects haven't configured their cocoapods naming, and are running with the default where they are named after the gradle module (eg \"shared\") then there's a possibility that both will upload a podspec file to the repository with the same name."))}d.isMDXComponent=!0}}]);