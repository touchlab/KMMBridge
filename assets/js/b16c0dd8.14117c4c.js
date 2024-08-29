"use strict";(self.webpackChunktouchlab=self.webpackChunktouchlab||[]).push([[3365],{3905:(e,t,a)=>{a.d(t,{Zo:()=>c,kt:()=>g});var n=a(7294);function o(e,t,a){return t in e?Object.defineProperty(e,t,{value:a,enumerable:!0,configurable:!0,writable:!0}):e[t]=a,e}function i(e,t){var a=Object.keys(e);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(e);t&&(n=n.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),a.push.apply(a,n)}return a}function r(e){for(var t=1;t<arguments.length;t++){var a=null!=arguments[t]?arguments[t]:{};t%2?i(Object(a),!0).forEach((function(t){o(e,t,a[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(a)):i(Object(a)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(a,t))}))}return e}function s(e,t){if(null==e)return{};var a,n,o=function(e,t){if(null==e)return{};var a,n,o={},i=Object.keys(e);for(n=0;n<i.length;n++)a=i[n],t.indexOf(a)>=0||(o[a]=e[a]);return o}(e,t);if(Object.getOwnPropertySymbols){var i=Object.getOwnPropertySymbols(e);for(n=0;n<i.length;n++)a=i[n],t.indexOf(a)>=0||Object.prototype.propertyIsEnumerable.call(e,a)&&(o[a]=e[a])}return o}var l=n.createContext({}),p=function(e){var t=n.useContext(l),a=t;return e&&(a="function"==typeof e?e(t):r(r({},t),e)),a},c=function(e){var t=p(e.components);return n.createElement(l.Provider,{value:t},e.children)},u="mdxType",d={inlineCode:"code",wrapper:function(e){var t=e.children;return n.createElement(n.Fragment,{},t)}},m=n.forwardRef((function(e,t){var a=e.components,o=e.mdxType,i=e.originalType,l=e.parentName,c=s(e,["components","mdxType","originalType","parentName"]),u=p(a),m=o,g=u["".concat(l,".").concat(m)]||u[m]||d[m]||i;return a?n.createElement(g,r(r({ref:t},c),{},{components:a})):n.createElement(g,r({ref:t},c))}));function g(e,t){var a=arguments,o=t&&t.mdxType;if("string"==typeof e||o){var i=a.length,r=new Array(i);r[0]=m;var s={};for(var l in t)hasOwnProperty.call(t,l)&&(s[l]=t[l]);s.originalType=e,s[u]="string"==typeof e?e:o,r[1]=s;for(var p=2;p<i;p++)r[p]=a[p];return n.createElement.apply(null,r)}return n.createElement.apply(null,a)}m.displayName="MDXCreateElement"},4530:(e,t,a)=>{a.r(t),a.d(t,{assets:()=>l,contentTitle:()=>r,default:()=>u,frontMatter:()=>i,metadata:()=>s,toc:()=>p});var n=a(7462),o=(a(7294),a(3905));const i={},r="Using Swift Package Manager",s={unversionedId:"spm/IOS_SPM",id:"spm/IOS_SPM",title:"Using Swift Package Manager",description:"Swift Package Manager is a newer dependency manager directly from Apple. In some ways it's more integrated into Xcode, but is also less flexible than CocoaPods. Much of that seems by design, as it's very difficult to introduce side effects into the Package.swift build scripts. While that is likely to result in more reliable builds for the average Xcode project, for Kotlin builds, that means some more manual processes at present.",source:"@site/docs/spm/01_IOS_SPM.md",sourceDirName:"spm",slug:"/spm/IOS_SPM",permalink:"/docs/spm/IOS_SPM",draft:!1,editUrl:"https://github.com/touchlab/KMMBridge/tree/main/website/docs/spm/01_IOS_SPM.md",tags:[],version:"current",lastUpdatedBy:"Kevin Galligan",lastUpdatedAt:1724891577,formattedLastUpdatedAt:"Aug 29, 2024",sidebarPosition:1,frontMatter:{},sidebar:"tutorialSidebar",previous:{title:"Swift Package Manager (SPM)",permalink:"/docs/category/swift-package-manager-spm"},next:{title:"SPM Local Dev Flow",permalink:"/docs/spm/IOS_LOCAL_DEV_SPM"}},l={},p=[{value:"Kotlin Project Configuration",id:"kotlin-project-configuration",level:2},{value:"Specifying swift tools version",id:"specifying-swift-tools-version",level:3},{value:"Specifying target platforms and versions",id:"specifying-target-platforms-and-versions",level:3},{value:"Using a custom package file",id:"using-a-custom-package-file",level:3},{value:"Exporting multiple frameworks",id:"exporting-multiple-frameworks",level:3},{value:"Artifact Authentication",id:"artifact-authentication",level:2},{value:"Xcode Configuration",id:"xcode-configuration",level:2},{value:"Updating Builds",id:"updating-builds",level:2},{value:"Local Kotlin Dev",id:"local-kotlin-dev",level:2}],c={toc:p};function u(e){let{components:t,...a}=e;return(0,o.kt)("wrapper",(0,n.Z)({},c,a,{components:t,mdxType:"MDXLayout"}),(0,o.kt)("h1",{id:"using-swift-package-manager"},"Using Swift Package Manager"),(0,o.kt)("p",null,"Swift Package Manager is a newer dependency manager directly from Apple. In some ways it's more integrated into Xcode, but is also less flexible than CocoaPods. Much of that seems by design, as it's very difficult to introduce side effects into the ",(0,o.kt)("inlineCode",{parentName:"p"},"Package.swift")," build scripts. While that is likely to result in more reliable builds for the average Xcode project, for Kotlin builds, that means some more manual processes at present."),(0,o.kt)("p",null,"Out of the box, the official Kotlin tools are far less integrated into SPM. We have some basic support for SPM development, but this is a work in progress. Feedback welcome."),(0,o.kt)("h2",{id:"kotlin-project-configuration"},"Kotlin Project Configuration"),(0,o.kt)("p",null,"After setting up KMMBridge in your Kotlin project, you should configure SPM for library publishing. Generally speaking, SPM wants to have the ",(0,o.kt)("inlineCode",{parentName:"p"},"Package.swift")," file in the root of the repo. Xcode and SPM use Git repos as an organizational and discovery unit. The ",(0,o.kt)("inlineCode",{parentName:"p"},"Package.swift")," file goes in the root, and Xcode clones from GitHub (or others) to read info about the library and source code."),(0,o.kt)("p",null,"If you don't have a ",(0,o.kt)("inlineCode",{parentName:"p"},"Package.swift")," file, or don't know how to set one up, that's OK. KMMBridge currently generates these files for you."),(0,o.kt)("p",null,"In the ",(0,o.kt)("inlineCode",{parentName:"p"},"kmmbridge")," block, add ",(0,o.kt)("inlineCode",{parentName:"p"},"spm()"),". If you call it without parameters, KMMBridge assumes you want the ",(0,o.kt)("inlineCode",{parentName:"p"},"Package.swift")," file at the root of your repo (we also assume you're using Git. We're not sure you could use SPM to deploy ",(0,o.kt)("em",{parentName:"p"},"without")," Git)."),(0,o.kt)("pre",null,(0,o.kt)("code",{parentName:"pre",className:"language-kotlin"},"kmmbridge {\n    spm()\n    // Other config...\n}\n")),(0,o.kt)("p",null,"In the example above, the Kotlin module is one folder down. The ",(0,o.kt)("inlineCode",{parentName:"p"},"spm()")," setup detects that with git automatically."),(0,o.kt)("p",null,(0,o.kt)("img",{parentName:"p",src:"https://tl-navigator-images.s3.us-east-1.amazonaws.com/docimages/2022-10-06_06-43-spmfolder.png",alt:"spmfolder"})),(0,o.kt)("p",null,"SPM uses Git for versioning. Your publication process will need to add tags to your repo as builds are published so that SPM can find those builds. Our ",(0,o.kt)("a",{parentName:"p",href:"../DEFAULT_GITHUB_FLOW"},"Default GitHub Flow")," handles versioning automatically."),(0,o.kt)("p",null,(0,o.kt)("strong",{parentName:"p"},"If you are configuring KMMBridge on your own"),", be aware that you need to set the Gradle ",(0,o.kt)("inlineCode",{parentName:"p"},"version")," property correctly, or provide a different way for KMMBridge's SPM support to get a version for publishing (see ",(0,o.kt)("a",{parentName:"p",href:"/docs/general/CONFIGURATION_OVERVIEW#versionmanager"},"Configuration Overview - VersionManager"),")"),(0,o.kt)("h3",{id:"specifying-swift-tools-version"},"Specifying swift tools version"),(0,o.kt)("p",null,"You can use the ",(0,o.kt)("inlineCode",{parentName:"p"},"swiftToolsVersion")," parameter to set the swift tools version that will be written to the Package.swift header. If no value is provided, version 5.3 will be used by default:"),(0,o.kt)("pre",null,(0,o.kt)("code",{parentName:"pre",className:"language-kotlin"},'kmmbridge {\n    ...\n    spm(swiftToolVersion = "5.8")\n}\n')),(0,o.kt)("h3",{id:"specifying-target-platforms-and-versions"},"Specifying target platforms and versions"),(0,o.kt)("p",null,"You can use the ",(0,o.kt)("inlineCode",{parentName:"p"},"targetPlatforms")," lambda to add a targets and versions. Currently, only iOS target is supported:"),(0,o.kt)("pre",null,(0,o.kt)("code",{parentName:"pre",className:"language-kotlin"},'kmmbridge {\n    ...\n    spm {\n        iOS { v("14") }\n        macOS { v("12") }\n    }\n}\n')),(0,o.kt)("h3",{id:"using-a-custom-package-file"},"Using a custom package file"),(0,o.kt)("p",null,"By default, KMMBridge fully manages your Package.swift file. This might not be what you want, if your published library needs to include more than just your Kotlin framework. If you need to customize your package file, pass the ",(0,o.kt)("inlineCode",{parentName:"p"},"useCustomPackageFile")," flag when configuring SPM in KMMBridge:"),(0,o.kt)("pre",null,(0,o.kt)("code",{parentName:"pre",className:"language-kotlin"},"kmmbridge {\n    ...\n    spm(useCustomPackageFile = true)\n}\n")),(0,o.kt)("p",null,"When this flag is set, rather than regenerating your entire package file during publication, KMMBridge will only update the variables it sets at the top of the package file. You are now responsible for using them correctly when making changes."),(0,o.kt)("p",null,"This works by replacing a block of code that begins with the comment ",(0,o.kt)("inlineCode",{parentName:"p"},"// BEGIN KMMBRIDGE VARIABLES BLOCK (do not edit)")," and ends with the comment ",(0,o.kt)("inlineCode",{parentName:"p"},"// END KMMBRIDGE BLOCK"),"."),(0,o.kt)("admonition",{type:"caution"},(0,o.kt)("p",{parentName:"admonition"},"The custom package file mode is new and experimental. The local dev flow using the ",(0,o.kt)("inlineCode",{parentName:"p"},"spmDevBuild")," gradle task is disabled when ",(0,o.kt)("inlineCode",{parentName:"p"},"useCustomPackageFile")," is true.")),(0,o.kt)("h3",{id:"exporting-multiple-frameworks"},"Exporting multiple frameworks"),(0,o.kt)("p",null,"By default, KMMBridge manages the Package.swift file considering that you have only Kotlin Module being exposed as a Swift Framework. If that's not your case, and you have more modules compiling Frameworks, you can use the ",(0,o.kt)("inlineCode",{parentName:"p"},"perModuleVariablesBlock")," flag together with the ",(0,o.kt)("inlineCode",{parentName:"p"},"useCustomPackageFile"),"."),(0,o.kt)("pre",null,(0,o.kt)("code",{parentName:"pre",className:"language-kotlin"},"kmmbridge {\n    ...\n    spm(useCustomPackageFile = true, perModuleVariablesBlock = true)\n}\n")),(0,o.kt)("p",null,"When set, it will modify the configuration block from KMMBridge to include the Framework name, so it can support multiple frameworks:"),(0,o.kt)("pre",null,(0,o.kt)("code",{parentName:"pre",className:"language-swift"},'///// Before\n\n// BEGIN KMMBRIDGE VARIABLES BLOCK (do not edit)\nlet remoteKotlinUrl = "https://www.example.com/"\nlet remoteKotlinChecksum = "01234567890abcdef"\nlet packageName = "TestPackage"\n// END KMMBRIDGE BLOCK\n\n///// After\n\n// BEGIN KMMBRIDGE VARIABLES BLOCK FOR \'TestPackage\' (do not edit)\nlet remoteTestPackageUrl = "https://www.example.com/"\nlet remoteTestPackageChecksum = "fedcba9876543210"\nlet testPackagePackageName = "TestPackage"\n// END KMMBRIDGE BLOCK FOR \'TestPackage\'\n\n// BEGIN KMMBRIDGE VARIABLES BLOCK FOR \'TestPackage2\' (do not edit)\nlet remoteTest2PackageUrl = "https://www.example.com/"\nlet remoteTestPackage2Checksum = "01234567890abcdeg"\nlet testPackage2PackageName = "TestPackage2"\n// END KMMBRIDGE BLOCK FOR \'TestPackage2\'\n')),(0,o.kt)("h2",{id:"artifact-authentication"},"Artifact Authentication"),(0,o.kt)("p",null,"For artifacts that are kept in private storage, you may need to add authentication information so your ",(0,o.kt)("inlineCode",{parentName:"p"},"~/.netrc")," file or your Mac's Keychain Access. See ",(0,o.kt)("a",{parentName:"p",href:"/docs/DEFAULT_GITHUB_FLOW#private-repos"},"the section here")," for a description of how to set up private file access."),(0,o.kt)("admonition",{type:"caution"},(0,o.kt)("p",{parentName:"admonition"},"When publishing to GitHub Packages, be aware of a few issues. First, GitHub Packages requires authentication for accessing files in ",(0,o.kt)("strong",{parentName:"p"},"all")," repos, public or private. You need to add ",(0,o.kt)("inlineCode",{parentName:"p"},"~/.netrc")," or Mac Keychain Access authentication info for any user accessing these repos. Also, if your repo is private, you'll need to authenticate to GitHub in Xcode to add it (see below), but you still also need to set up ",(0,o.kt)("inlineCode",{parentName:"p"},"~/.netrc")," or Mac Keychain Access for SPM to grab the binary.")),(0,o.kt)("h2",{id:"xcode-configuration"},"Xcode Configuration"),(0,o.kt)("p",null,"Open or create an Xcode project. To add an SPM package, go to ",(0,o.kt)("inlineCode",{parentName:"p"},"File > Add Packages"),' in the Xcode menu. Add your source control account (presumably GitHub). You can usually browse for the package at that point, but depending on how many repos you have, it may be easier to copy/paste the repo URL in the top/right search bar. After finding the package, you should generally add the pacakge by version ("Up to Next Major Version" suggested).'),(0,o.kt)("p",null,(0,o.kt)("img",{parentName:"p",src:"https://tl-navigator-images.s3.us-east-1.amazonaws.com/docimages/2022-10-06_06-57-addpackages.png",alt:"addpackages"})),(0,o.kt)("admonition",{type:"warning"},(0,o.kt)("p",{parentName:"admonition"},"The Xcode configuration can be confusing here. You need to authenticate to GitHub through the Xcode UI if you have a private GitHub repo. You will ",(0,o.kt)("em",{parentName:"p"},"still")," need to set up authentication for SPM to access the actual packages with ",(0,o.kt)("inlineCode",{parentName:"p"},"~/.netrc"),". This has been the ",(0,o.kt)("em",{parentName:"p"},"primary")," source of issues when people set up KMMBridge!!!")),(0,o.kt)("p",null,"Once added, you should be able to import the Kotlin module into Swift/Objc files and build!"),(0,o.kt)("p",null,(0,o.kt)("img",{parentName:"p",src:"https://tl-navigator-images.s3.us-east-1.amazonaws.com/docimages/2022-10-06_07-00-import.png",alt:"import"})),(0,o.kt)("h2",{id:"updating-builds"},"Updating Builds"),(0,o.kt)("p",null,'Run the KMMBridge build again. It should automatically create another build version and publish that to the GitHub repo. In Xcode, you can update your imported version by right-clicking on the module and selecting "Update Package":'),(0,o.kt)("p",null,(0,o.kt)("img",{parentName:"p",src:"https://tl-navigator-images.s3.us-east-1.amazonaws.com/docimages/2022-10-06_07-04-updatepackage.png",alt:"updatepackage"})),(0,o.kt)("p",null,(0,o.kt)("img",{parentName:"p",src:"https://tl-navigator-images.s3.us-east-1.amazonaws.com/docimages/2022-10-06_07-17-updatepackagedone.png",alt:"updatepackagedone"})),(0,o.kt)("h2",{id:"local-kotlin-dev"},"Local Kotlin Dev"),(0,o.kt)("p",null,"If you are going to locally build and test Kotlin with SPM, see  ",(0,o.kt)("a",{parentName:"p",href:"/docs/spm/IOS_LOCAL_DEV_SPM"},"IOS_LOCAL_DEV_SPM"),"."))}u.isMDXComponent=!0}}]);