"use strict";(self.webpackChunktouchlab=self.webpackChunktouchlab||[]).push([[422],{3905:(e,t,n)=>{n.d(t,{Zo:()=>p,kt:()=>g});var a=n(7294);function r(e,t,n){return t in e?Object.defineProperty(e,t,{value:n,enumerable:!0,configurable:!0,writable:!0}):e[t]=n,e}function i(e,t){var n=Object.keys(e);if(Object.getOwnPropertySymbols){var a=Object.getOwnPropertySymbols(e);t&&(a=a.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),n.push.apply(n,a)}return n}function o(e){for(var t=1;t<arguments.length;t++){var n=null!=arguments[t]?arguments[t]:{};t%2?i(Object(n),!0).forEach((function(t){r(e,t,n[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(n)):i(Object(n)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(n,t))}))}return e}function l(e,t){if(null==e)return{};var n,a,r=function(e,t){if(null==e)return{};var n,a,r={},i=Object.keys(e);for(a=0;a<i.length;a++)n=i[a],t.indexOf(n)>=0||(r[n]=e[n]);return r}(e,t);if(Object.getOwnPropertySymbols){var i=Object.getOwnPropertySymbols(e);for(a=0;a<i.length;a++)n=i[a],t.indexOf(n)>=0||Object.prototype.propertyIsEnumerable.call(e,n)&&(r[n]=e[n])}return r}var s=a.createContext({}),u=function(e){var t=a.useContext(s),n=t;return e&&(n="function"==typeof e?e(t):o(o({},t),e)),n},p=function(e){var t=u(e.components);return a.createElement(s.Provider,{value:t},e.children)},c="mdxType",m={inlineCode:"code",wrapper:function(e){var t=e.children;return a.createElement(a.Fragment,{},t)}},d=a.forwardRef((function(e,t){var n=e.components,r=e.mdxType,i=e.originalType,s=e.parentName,p=l(e,["components","mdxType","originalType","parentName"]),c=u(n),d=r,g=c["".concat(s,".").concat(d)]||c[d]||m[d]||i;return n?a.createElement(g,o(o({ref:t},p),{},{components:n})):a.createElement(g,o({ref:t},p))}));function g(e,t){var n=arguments,r=t&&t.mdxType;if("string"==typeof e||r){var i=n.length,o=new Array(i);o[0]=d;var l={};for(var s in t)hasOwnProperty.call(t,s)&&(l[s]=t[s]);l.originalType=e,l[c]="string"==typeof e?e:r,o[1]=l;for(var u=2;u<i;u++)o[u]=n[u];return a.createElement.apply(null,o)}return a.createElement.apply(null,n)}d.displayName="MDXCreateElement"},4735:(e,t,n)=>{n.r(t),n.d(t,{assets:()=>s,contentTitle:()=>o,default:()=>c,frontMatter:()=>i,metadata:()=>l,toc:()=>u});var a=n(7462),r=(n(7294),n(3905));const i={sidebar_position:1},o="Configuration Overview",l={unversionedId:"general/CONFIGURATION_OVERVIEW",id:"general/CONFIGURATION_OVERVIEW",title:"Configuration Overview",description:"Artifact Managers",source:"@site/docs/general/CONFIGURATION_OVERVIEW.md",sourceDirName:"general",slug:"/general/CONFIGURATION_OVERVIEW",permalink:"/KMMBridge/general/CONFIGURATION_OVERVIEW",draft:!1,editUrl:"https://github.com/touchlab/KMMBridge/tree/main/website/docs/general/CONFIGURATION_OVERVIEW.md",tags:[],version:"current",lastUpdatedBy:"Russell Wolf",lastUpdatedAt:1677179061,formattedLastUpdatedAt:"Feb 23, 2023",sidebarPosition:1,frontMatter:{sidebar_position:1},sidebar:"tutorialSidebar",previous:{title:"General Documentation",permalink:"/KMMBridge/category/general-documentation"},next:{title:"Android Versioning",permalink:"/KMMBridge/general/ANDROID_VERSIONING"}},s={},u=[{value:"Artifact Managers",id:"artifact-managers",level:2},{value:"Maven Repository Artifacts",id:"maven-repository-artifacts",level:3},{value:"S3 Public Artifacts",id:"s3-public-artifacts",level:3},{value:"Dependency Managers",id:"dependency-managers",level:2},{value:"Version Managers",id:"version-managers",level:2},{value:"Incrementing Version Managers",id:"incrementing-version-managers",level:3},{value:"GitTagVersionManager",id:"gittagversionmanager",level:3},{value:"GithubReleaseVersionManager",id:"githubreleaseversionmanager",level:3},{value:"TimestampVersionManager",id:"timestampversionmanager",level:3},{value:"ManualVersionManager",id:"manualversionmanager",level:3},{value:"Version Writer",id:"version-writer",level:2},{value:"Naming",id:"naming",level:2}],p={toc:u};function c(e){let{components:t,...n}=e;return(0,r.kt)("wrapper",(0,a.Z)({},p,n,{components:t,mdxType:"MDXLayout"}),(0,r.kt)("h1",{id:"configuration-overview"},"Configuration Overview"),(0,r.kt)("h2",{id:"artifact-managers"},"Artifact Managers"),(0,r.kt)("p",null,"Artifact Managers handle uploading the binary and generate the url that will be used to access the binary. These implementations are very specific to the back end hosting being used. Current implementations:"),(0,r.kt)("h3",{id:"maven-repository-artifacts"},(0,r.kt)("a",{parentName:"h3",href:"../artifacts/MAVEN_REPO_ARTIFACTS"},"Maven Repository Artifacts")),(0,r.kt)("p",null,"This is the simplest to configure and currently the recommended best way to publish your binaries. If you are using GitHub, you can publish to Github Packages easily with our integration tools. For a complete introduction and overview of how to configure your project with this manager, see ",(0,r.kt)("a",{parentName:"p",href:"/KMMBridge/DEFAULT_GITHUB_FLOW"},"Default GitHub Flow"),"."),(0,r.kt)("h3",{id:"s3-public-artifacts"},(0,r.kt)("a",{parentName:"h3",href:"/KMMBridge/artifacts/S3_PUBLIC_ARTIFACTS"},"S3 Public Artifacts")),(0,r.kt)("p",null,"This implementation will publish to your S3 bucket. By default it will set the access to public. You can also have access be private and controlled by AWS access, but there is no easy way to give Xcode access to your binaries. You'll need to configure machine access to these buckets (this is more common in larger enterprise environment)."),(0,r.kt)("p",null,"The S3 artifact manager is really the starting point for teams that need a more custom implementation (Azure, Google Cloud, private hosting, etc)."),(0,r.kt)("admonition",{title:"Github Release Artifacts",type:"note"},(0,r.kt)("p",{parentName:"admonition"},"KMMBridge previously had a separate ",(0,r.kt)("inlineCode",{parentName:"p"},"githubReleaseArtifacts()")," option, but it is no longer supported in versions 0.3.5+. The reason is it doesn't provide good enough benefits over using the ",(0,r.kt)("a",{parentName:"p",href:"../artifacts/MAVEN_REPO_ARTIFACTS"},"maven artifact manager")," to publish to Github packages. If using an old version of KMMBridge you can find the documentation ",(0,r.kt)("a",{parentName:"p",href:"/github_release_artifacts"},"here"))),(0,r.kt)("h2",{id:"dependency-managers"},"Dependency Managers"),(0,r.kt)("p",null,"Dependency managers handle integration with CocoaPods and SPM. They manage generating the config files (podspec or Package.swift), and the publishing of the releases. There are currently only two implementations:"),(0,r.kt)("ul",null,(0,r.kt)("li",{parentName:"ul"},"CocoapodsDependencyManager: ",(0,r.kt)("a",{parentName:"li",href:"/KMMBridge/cocoapods/IOS_COCOAPODS"},"IOS_COCOAPODS")," "),(0,r.kt)("li",{parentName:"ul"},"SpmDependencyManager: ",(0,r.kt)("a",{parentName:"li",href:"/KMMBridge/spm/IOS_SPM"},"IOS_SPM"))),(0,r.kt)("h2",{id:"version-managers"},"Version Managers"),(0,r.kt)("p",null,"KMMBridge is designed to allow you to publish updates to your iOS Kotlin code as development progresses. That often means several minor versions in between major ones. To support this, we've added an interface called ",(0,r.kt)("inlineCode",{parentName:"p"},"VersionManager")," that handles this for you."),(0,r.kt)("p",null,"There are two basic options: automatically incrementing version managers, and exact Gradle version. The automatically incrementing versions exist for teams that will publish more frequent iOS builds while dev is ongoing. Each publish will automatically increment a minor version. If you plan to directly manage versions for the whole project, you can just tell KMMBridge to use the Gradle version."),(0,r.kt)("blockquote",null,(0,r.kt)("p",{parentName:"blockquote"},"In the current version of KMMBridge the Android version is not automatically incremented while the iOS version is. If you need the versions to be aligned, you need to manage the versions manually (by using ",(0,r.kt)("a",{parentName:"p",href:"#ManualVersionManager"},"ManualVersionManager"),").")),(0,r.kt)("h3",{id:"incrementing-version-managers"},"Incrementing Version Managers"),(0,r.kt)("p",null,"All incrementing version managers need a base version, which should be the first two numbers of a three number semantic versioning scheme. CocoaPods, and especially SPM, do not work well with less structured version strings."),(0,r.kt)("p",null,'You can either set your Gradle version to something like "0.3", or explicitly set ',(0,r.kt)("inlineCode",{parentName:"p"},"versionPrefix")," in the ",(0,r.kt)("inlineCode",{parentName:"p"},"kmmbridge")," config block. ",(0,r.kt)("inlineCode",{parentName:"p"},"versionPrefix")," will override whatever your Gradle version is set to."),(0,r.kt)("p",null,"When you publish new iOS builds, you get release a history like the following:"),(0,r.kt)("ul",null,(0,r.kt)("li",{parentName:"ul"},"0.3.0"),(0,r.kt)("li",{parentName:"ul"},"0.3.1"),(0,r.kt)("li",{parentName:"ul"},"0.3.2"),(0,r.kt)("li",{parentName:"ul"},"etc...")),(0,r.kt)("p",null,"We don't set a version manager by default, so you'll need to select something. The version managers provided with KMMBridge are the following:"),(0,r.kt)("h3",{id:"gittagversionmanager"},"GitTagVersionManager"),(0,r.kt)("p",null,"Versions with Git tags. Increments by one each time."),(0,r.kt)("pre",null,(0,r.kt)("code",{parentName:"pre",className:"language-kotlin"},"kmmbridge {\n  gitTagVersions()\n}\n")),(0,r.kt)("h3",{id:"githubreleaseversionmanager"},"GithubReleaseVersionManager"),(0,r.kt)("p",null,"Similar to GitTagVersionManager, but calls the GitHub api to create a Git release. Only usable with GitHub, but prefferred to GitTagVersionManager if you are using GitHub."),(0,r.kt)("pre",null,(0,r.kt)("code",{parentName:"pre",className:"language-kotlin"},"kmmbridge {\n  githubReleaseVersions()\n}\n")),(0,r.kt)("h3",{id:"timestampversionmanager"},"TimestampVersionManager"),(0,r.kt)("p",null,"Use the current timestamp, which should mean increasing values (unless various server timestamps are extremenly off)."),(0,r.kt)("pre",null,(0,r.kt)("code",{parentName:"pre",className:"language-kotlin"},"kmmbridge {\n  timestampVersions()\n}\n")),(0,r.kt)("p",null,"Generally speaking, you should use GitTagVersionManager over TimestampVersionManager, but both will work fine."),(0,r.kt)("h3",{id:"manualversionmanager"},"ManualVersionManager"),(0,r.kt)("p",null,"No automatic versioning. This version manager will get the version from Gradle (or the Jetbrains CocoaPods config, if set separately there). You'll need to make sure to increment this version if you're publishing a new version."),(0,r.kt)("p",null,"(Note: ",(0,r.kt)("a",{parentName:"p",href:"https://github.com/touchlab/KMMBridge/issues/58"},"KMMBridge/issues/58")," is pending. CocoaPods may overwrite rather than fail when publishing)"),(0,r.kt)("pre",null,(0,r.kt)("code",{parentName:"pre",className:"language-kotlin"},"kmmbridge {\n  manualVersions()\n}\n")),(0,r.kt)("h2",{id:"version-writer"},"Version Writer"),(0,r.kt)("p",null,"You usually don't need to worry about the version writer. Depending on the version manager you're using, and if you're publishing to SPM,\nthere are git operations that need to be run. If you're doing a lot of coordinated git operations in your CI workflow, you may want to control\nor disable KMMBridge git operations."),(0,r.kt)("p",null,"You can simply disable all git operations by calling the following in config:"),(0,r.kt)("pre",null,(0,r.kt)("code",{parentName:"pre",className:"language-kotlin"},"kmmbridge { \n  noGitOperations()\n}\n")),(0,r.kt)("p",null,"Alternatively, you can set a custom version writer:"),(0,r.kt)("pre",null,(0,r.kt)("code",{parentName:"pre",className:"language-kotlin"},"kmmbridge {\n    versionWriter.set(myWriter)\n}\n")),(0,r.kt)("admonition",{type:"caution"},(0,r.kt)("p",{parentName:"admonition"},"The git operations necessary to manage versions can be complex. Managing this on your own can introduce\nerrors that are difficult to diagnose.")),(0,r.kt)("h2",{id:"naming"},"Naming"),(0,r.kt)("p",null,"In Kotlin code you can set the name of your Framework as well as the name of your Podfile (when using cocoapods)."),(0,r.kt)("p",null,(0,r.kt)("strong",{parentName:"p"},"Framework base name")," controls the name that will eventually be used in the Swift ",(0,r.kt)("inlineCode",{parentName:"p"},"import")," statement."),(0,r.kt)("p",null,"When using Cocoapods:"),(0,r.kt)("pre",null,(0,r.kt)("code",{parentName:"pre",className:"language-kotlin"},'kotlin {\n    cocoapods {\n        framework {\n            baseName = "FRAMEWORKNAME"\n        }\n    }\n}\n')),(0,r.kt)("p",null,"When using SPM you can set the framework name in the ",(0,r.kt)("inlineCode",{parentName:"p"},"kmmbridge")," block:"),(0,r.kt)("pre",null,(0,r.kt)("code",{parentName:"pre",className:"language-kotlin"},'kmmbridge {\n    frameworkName.set("FRAMEWORKNAME")\n}\n')),(0,r.kt)("p",null,"Cocoapods only:\n",(0,r.kt)("strong",{parentName:"p"},"Podfile name")," controls the name that will eventually be used in the iOS Podfile, and is the name of the podspec file. This is written to the podspec in the ",(0,r.kt)("inlineCode",{parentName:"p"},"spec.name")," field."),(0,r.kt)("pre",null,(0,r.kt)("code",{parentName:"pre",className:"language-kotlin"},'kotlin {\n    cocoapods {\n        name = "PODNAME"\n    }\n}\n')),(0,r.kt)("p",null,"The podspec is uploaded to a folder in the podspec repo based on the KMMBridge version and the Podfile name. Therefore, the path looks like this:"),(0,r.kt)("pre",null,(0,r.kt)("code",{parentName:"pre"},"<podspec-repo-url>/<podname>/<kmm-version>/<podname>.podspec\n")),(0,r.kt)("p",null,"There is a danger of having naming conflicts. If two projects haven't configured their cocoapods naming, and are running with the default where they are named after the gradle module (eg \"shared\") then there's a possibility that both will upload a podspec file to the repository with the same name."))}c.isMDXComponent=!0}}]);