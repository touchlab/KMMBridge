"use strict";(self.webpackChunktouchlab=self.webpackChunktouchlab||[]).push([[8973],{3905:(e,t,a)=>{a.d(t,{Zo:()=>p,kt:()=>g});var n=a(7294);function i(e,t,a){return t in e?Object.defineProperty(e,t,{value:a,enumerable:!0,configurable:!0,writable:!0}):e[t]=a,e}function o(e,t){var a=Object.keys(e);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(e);t&&(n=n.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),a.push.apply(a,n)}return a}function r(e){for(var t=1;t<arguments.length;t++){var a=null!=arguments[t]?arguments[t]:{};t%2?o(Object(a),!0).forEach((function(t){i(e,t,a[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(a)):o(Object(a)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(a,t))}))}return e}function s(e,t){if(null==e)return{};var a,n,i=function(e,t){if(null==e)return{};var a,n,i={},o=Object.keys(e);for(n=0;n<o.length;n++)a=o[n],t.indexOf(a)>=0||(i[a]=e[a]);return i}(e,t);if(Object.getOwnPropertySymbols){var o=Object.getOwnPropertySymbols(e);for(n=0;n<o.length;n++)a=o[n],t.indexOf(a)>=0||Object.prototype.propertyIsEnumerable.call(e,a)&&(i[a]=e[a])}return i}var l=n.createContext({}),u=function(e){var t=n.useContext(l),a=t;return e&&(a="function"==typeof e?e(t):r(r({},t),e)),a},p=function(e){var t=u(e.components);return n.createElement(l.Provider,{value:t},e.children)},c="mdxType",d={inlineCode:"code",wrapper:function(e){var t=e.children;return n.createElement(n.Fragment,{},t)}},m=n.forwardRef((function(e,t){var a=e.components,i=e.mdxType,o=e.originalType,l=e.parentName,p=s(e,["components","mdxType","originalType","parentName"]),c=u(a),m=i,g=c["".concat(l,".").concat(m)]||c[m]||d[m]||o;return a?n.createElement(g,r(r({ref:t},p),{},{components:a})):n.createElement(g,r({ref:t},p))}));function g(e,t){var a=arguments,i=t&&t.mdxType;if("string"==typeof e||i){var o=a.length,r=new Array(o);r[0]=m;var s={};for(var l in t)hasOwnProperty.call(t,l)&&(s[l]=t[l]);s.originalType=e,s[c]="string"==typeof e?e:i,r[1]=s;for(var u=2;u<o;u++)r[u]=a[u];return n.createElement.apply(null,r)}return n.createElement.apply(null,a)}m.displayName="MDXCreateElement"},7519:(e,t,a)=>{a.r(t),a.d(t,{assets:()=>l,contentTitle:()=>r,default:()=>c,frontMatter:()=>o,metadata:()=>s,toc:()=>u});var n=a(7462),i=(a(7294),a(3905));const o={},r="Using Swift Package Manager",s={unversionedId:"spm/IOS_SPM",id:"version-0.3.x/spm/IOS_SPM",title:"Using Swift Package Manager",description:"Swift Package Manager is a newer dependency manager directly from Apple. In some ways it's more integrated into Xcode, but is also less flexible than CocoaPods. Much of that seems by design, as it's very difficult to introduce side effects into the Package.swift build scripts. While that is likely to result in more reliable builds for the average Xcode project, for Kotlin builds, that means some more manual processes at present.",source:"@site/versioned_docs/version-0.3.x/spm/01_IOS_SPM.md",sourceDirName:"spm",slug:"/spm/IOS_SPM",permalink:"/docs/0.3.x/spm/IOS_SPM",draft:!1,editUrl:"https://github.com/touchlab/KMMBridge/tree/main/website/versioned_docs/version-0.3.x/spm/01_IOS_SPM.md",tags:[],version:"0.3.x",lastUpdatedBy:"Justin Mancinelli",lastUpdatedAt:1715352613,formattedLastUpdatedAt:"May 10, 2024",sidebarPosition:1,frontMatter:{},sidebar:"tutorialSidebar",previous:{title:"Swift Package Manager (SPM)",permalink:"/docs/0.3.x/category/swift-package-manager-spm"},next:{title:"SPM Local Dev Flow",permalink:"/docs/0.3.x/spm/IOS_LOCAL_DEV_SPM"}},l={},u=[{value:"Kotlin Project Configuration",id:"kotlin-project-configuration",level:2},{value:"Commit manually",id:"commit-manually",level:3},{value:"Using a custom package file",id:"using-a-custom-package-file",level:3},{value:"Setting up SPM without using the Cocoapods plugin",id:"setting-up-spm-without-using-the-cocoapods-plugin",level:3},{value:"Artifact Authentication",id:"artifact-authentication",level:2},{value:"Xcode Configuration",id:"xcode-configuration",level:2},{value:"Updating Builds",id:"updating-builds",level:2},{value:"Local Kotlin Dev",id:"local-kotlin-dev",level:2}],p={toc:u};function c(e){let{components:t,...a}=e;return(0,i.kt)("wrapper",(0,n.Z)({},p,a,{components:t,mdxType:"MDXLayout"}),(0,i.kt)("h1",{id:"using-swift-package-manager"},"Using Swift Package Manager"),(0,i.kt)("p",null,"Swift Package Manager is a newer dependency manager directly from Apple. In some ways it's more integrated into Xcode, but is also less flexible than CocoaPods. Much of that seems by design, as it's very difficult to introduce side effects into the ",(0,i.kt)("inlineCode",{parentName:"p"},"Package.swift")," build scripts. While that is likely to result in more reliable builds for the average Xcode project, for Kotlin builds, that means some more manual processes at present."),(0,i.kt)("p",null,"Out of the box, the official Kotlin tools are far less integrated into SPM. We have some basic support for SPM development, but this is a work in progress. Feedback welcome."),(0,i.kt)("h2",{id:"kotlin-project-configuration"},"Kotlin Project Configuration"),(0,i.kt)("p",null,"After setting up KMMBridge in your Kotlin project, you should configure SPM for library publishing. Generally speaking, SPM wants to have the ",(0,i.kt)("inlineCode",{parentName:"p"},"Package.swift")," file in the root of the repo. Xcode and SPM use Git repos as an organizational and discovery unit. The ",(0,i.kt)("inlineCode",{parentName:"p"},"Package.swift")," file goes in the root, and Xcode clones from GitHub (or others) to read info about the library and source code."),(0,i.kt)("p",null,"If you don't have a ",(0,i.kt)("inlineCode",{parentName:"p"},"Package.swift")," file, or don't know how to set one up, that's OK. KMMBridge currently generates these files for you."),(0,i.kt)("p",null,"In the ",(0,i.kt)("inlineCode",{parentName:"p"},"kmmbridge")," block, add ",(0,i.kt)("inlineCode",{parentName:"p"},"spm()"),". If you call it without parameters, KMMBridge assumes you want the ",(0,i.kt)("inlineCode",{parentName:"p"},"Package.swift")," file at the root of your repo (we also assume you're using Git)."),(0,i.kt)("pre",null,(0,i.kt)("code",{parentName:"pre",className:"language-kotlin"},"kmmbridge {\n    spm()\n    // Other config...\n}\n")),(0,i.kt)("p",null,"In the example above, the Kotlin module is one folder down. The ",(0,i.kt)("inlineCode",{parentName:"p"},"spm()")," setup detects that with git automatically."),(0,i.kt)("p",null,(0,i.kt)("img",{parentName:"p",src:"https://tl-navigator-images.s3.us-east-1.amazonaws.com/docimages/2022-10-06_06-43-spmfolder.png",alt:"spmfolder"})),(0,i.kt)("p",null,"SPM uses Git for versioning, so you'll probably want to use either Git tag or GitHub release version managers, and at least at launch, likely want to use GitHub artifacts."),(0,i.kt)("p",null,"Here is the suggested config for SPM:"),(0,i.kt)("pre",null,(0,i.kt)("code",{parentName:"pre",className:"language-kotlin"},'kmmbridge {\n    mavenPublishArtifacts()\n    githubReleaseVersions()\n    versionPrefix.set("0.1")\n    spm()\n}\n')),(0,i.kt)("h3",{id:"commit-manually"},"Commit manually"),(0,i.kt)("p",null,"There is an option to disable git operations and commit the generated ",(0,i.kt)("inlineCode",{parentName:"p"},"Package.swift")," by yourself.\nTypical usage would be when using ",(0,i.kt)("inlineCode",{parentName:"p"},"manualVersions()")," or ",(0,i.kt)("inlineCode",{parentName:"p"},"timestampVersions()")," to avoid all automatic git interaction.\nUsing this flow is somewhat dangerous, as the publication state is inconsistent at the end of the kmmBridgePublish task.\nThe user is responsible for manually committing the updated package file, or else the new version will not be available to downstream consumers.\nWhen using ",(0,i.kt)("inlineCode",{parentName:"p"},"gitTagVersions()")," if you don't commit and tag the newest version, the next publishing might fail because the version is not incremented correctly."),(0,i.kt)("p",null,"To do so, call ",(0,i.kt)("inlineCode",{parentName:"p"},"noGitOperations()")," in your ",(0,i.kt)("inlineCode",{parentName:"p"},"kmmbridge")," block."),(0,i.kt)("pre",null,(0,i.kt)("code",{parentName:"pre",className:"language-kotlin"},"kmmbridge {\n    spm()\n    noGitOperations()\n    // Other config...\n}\n")),(0,i.kt)("p",null,"Once this is all set up, run a build so you have at least one version available."),(0,i.kt)("h3",{id:"using-a-custom-package-file"},"Using a custom package file"),(0,i.kt)("p",null,"By default, KMMBridge fully manages your Package.swift file. This might not be what you want, if your published library needs to include more than just your Kotlin framework. If you need to customize your package file, pass the ",(0,i.kt)("inlineCode",{parentName:"p"},"useCustomPackageFile")," flag when configuring SPM in KMMBridge:"),(0,i.kt)("pre",null,(0,i.kt)("code",{parentName:"pre",className:"language-kotlin"},"kmmbridge {\n    ...\n    spm(useCustomPackageFile = true)\n}\n")),(0,i.kt)("p",null,"When this flag is set, rather than regenerating your entire package file during publication, KMMBridge will only update the variables it sets at the top of the package file. You are now responsible for using them correctly when making changes."),(0,i.kt)("p",null,"This works by replacing a block of code that begins with the comment ",(0,i.kt)("inlineCode",{parentName:"p"},"// BEGIN KMMBRIDGE VARIABLES BLOCK (do not edit)")," and ends with the comment ",(0,i.kt)("inlineCode",{parentName:"p"},"// END KMMBRIDGE BLOCK"),"."),(0,i.kt)("admonition",{type:"caution"},(0,i.kt)("p",{parentName:"admonition"},"The custom package file mode is new and experimental. The local dev flow using the ",(0,i.kt)("inlineCode",{parentName:"p"},"spmDevBuild")," gradle task is disabled when ",(0,i.kt)("inlineCode",{parentName:"p"},"useCustomPackageFile")," is true.")),(0,i.kt)("h3",{id:"setting-up-spm-without-using-the-cocoapods-plugin"},"Setting up SPM without using the Cocoapods plugin"),(0,i.kt)("p",null,"You can use KMMBridge with only SPM, without Cocoapods plugin, but there are some differences in setting things up."),(0,i.kt)("p",null,"If you want to set the framework to be static or dynamic, you will need to access binaries of each target.\nYou can also set the framework name in the parameter of the ",(0,i.kt)("inlineCode",{parentName:"p"},"framework")," function."),(0,i.kt)("pre",null,(0,i.kt)("code",{parentName:"pre",className:"language-kotlin"},'kotlin {\n    iosX64 { // can be set for any of your tagets\n        binaries {\n            framework("FRAMEWORK_NAME") {\n                isStatic = true // or false for dynamic framework\n            }\n        }\n    }\n}\n')),(0,i.kt)("p",null,"Or you can set it for all the targets at once, just be careful not to have other kotlin native targets that you don't want to set this for (you can use filter if needed):"),(0,i.kt)("pre",null,(0,i.kt)("code",{parentName:"pre",className:"language-kotlin"},'targets.withType<KotlinNativeTarget> {\n    binaries {\n        framework("FRAMEWORK_NAME") {\n            isStatic = true // or false for dynamic framework\n        }\n    }\n}\n')),(0,i.kt)("p",null,"You can check out ",(0,i.kt)("a",{parentName:"p",href:"https://github.com/touchlab/KmmBridgeIntegrationTest-SPMWithoutCommit/blob/main/shared/build.gradle.kts"},"build.gradle in this test")," as an example."),(0,i.kt)("h2",{id:"artifact-authentication"},"Artifact Authentication"),(0,i.kt)("p",null,"For artifacts that are kept in private storage, you may need to add authentication information so your ",(0,i.kt)("inlineCode",{parentName:"p"},"~/.netrc")," file or your Mac's Keychain Access. See ",(0,i.kt)("a",{parentName:"p",href:"/docs/0.3.x/DEFAULT_GITHUB_FLOW#private-repos"},"the section here")," for a description of how to set up private file access."),(0,i.kt)("admonition",{type:"caution"},(0,i.kt)("p",{parentName:"admonition"},"When you access repos in GitHub with Xcode, you need to authenticate to GitHub. That isn't enough to access private GitHub release artifacts. You ",(0,i.kt)("em",{parentName:"p"},"also")," need to add ",(0,i.kt)("inlineCode",{parentName:"p"},"~/.netrc")," or Mac Keychain Access authentication info. ")),(0,i.kt)("h2",{id:"xcode-configuration"},"Xcode Configuration"),(0,i.kt)("p",null,"Open or create an Xcode project. To add an SPM package, go to ",(0,i.kt)("inlineCode",{parentName:"p"},"File > Add Packages"),' in the Xcode menu. Add your source control account (presumably GitHub). You can usually browse for the package at that point, but depending on how many repos you have, it may be easier to copy/paste the repo URL in the top/right search bar. After finding the package, you should generally add the pacakge by version ("Up to Next Major Version" suggested).'),(0,i.kt)("p",null,(0,i.kt)("img",{parentName:"p",src:"https://tl-navigator-images.s3.us-east-1.amazonaws.com/docimages/2022-10-06_06-57-addpackages.png",alt:"addpackages"})),(0,i.kt)("p",null,"Once added, you should be able to import the Kotlin module into Swift/Objc files and build!"),(0,i.kt)("p",null,(0,i.kt)("img",{parentName:"p",src:"https://tl-navigator-images.s3.us-east-1.amazonaws.com/docimages/2022-10-06_07-00-import.png",alt:"import"})),(0,i.kt)("h2",{id:"updating-builds"},"Updating Builds"),(0,i.kt)("p",null,'Run the KMMBridge build again. It should automatically create another build version and publish that to the GitHub repo. In Xcode, you can update your imported version by right-clicking on the module and selecting "Update Package":'),(0,i.kt)("p",null,(0,i.kt)("img",{parentName:"p",src:"https://tl-navigator-images.s3.us-east-1.amazonaws.com/docimages/2022-10-06_07-04-updatepackage.png",alt:"updatepackage"})),(0,i.kt)("p",null,(0,i.kt)("img",{parentName:"p",src:"https://tl-navigator-images.s3.us-east-1.amazonaws.com/docimages/2022-10-06_07-17-updatepackagedone.png",alt:"updatepackagedone"})),(0,i.kt)("h2",{id:"local-kotlin-dev"},"Local Kotlin Dev"),(0,i.kt)("p",null,"If you are going to locally build and test Kotlin with SPM, see  ",(0,i.kt)("a",{parentName:"p",href:"/docs/0.3.x/spm/IOS_LOCAL_DEV_SPM"},"IOS_LOCAL_DEV_SPM"),"."))}c.isMDXComponent=!0}}]);