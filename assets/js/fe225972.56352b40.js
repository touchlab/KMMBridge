"use strict";(self.webpackChunktouchlab=self.webpackChunktouchlab||[]).push([[2281],{3905:(e,t,a)=>{a.d(t,{Zo:()=>u,kt:()=>m});var o=a(7294);function i(e,t,a){return t in e?Object.defineProperty(e,t,{value:a,enumerable:!0,configurable:!0,writable:!0}):e[t]=a,e}function n(e,t){var a=Object.keys(e);if(Object.getOwnPropertySymbols){var o=Object.getOwnPropertySymbols(e);t&&(o=o.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),a.push.apply(a,o)}return a}function r(e){for(var t=1;t<arguments.length;t++){var a=null!=arguments[t]?arguments[t]:{};t%2?n(Object(a),!0).forEach((function(t){i(e,t,a[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(a)):n(Object(a)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(a,t))}))}return e}function l(e,t){if(null==e)return{};var a,o,i=function(e,t){if(null==e)return{};var a,o,i={},n=Object.keys(e);for(o=0;o<n.length;o++)a=n[o],t.indexOf(a)>=0||(i[a]=e[a]);return i}(e,t);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(e);for(o=0;o<n.length;o++)a=n[o],t.indexOf(a)>=0||Object.prototype.propertyIsEnumerable.call(e,a)&&(i[a]=e[a])}return i}var s=o.createContext({}),p=function(e){var t=o.useContext(s),a=t;return e&&(a="function"==typeof e?e(t):r(r({},t),e)),a},u=function(e){var t=p(e.components);return o.createElement(s.Provider,{value:t},e.children)},d="mdxType",c={inlineCode:"code",wrapper:function(e){var t=e.children;return o.createElement(o.Fragment,{},t)}},h=o.forwardRef((function(e,t){var a=e.components,i=e.mdxType,n=e.originalType,s=e.parentName,u=l(e,["components","mdxType","originalType","parentName"]),d=p(a),h=i,m=d["".concat(s,".").concat(h)]||d[h]||c[h]||n;return a?o.createElement(m,r(r({ref:t},u),{},{components:a})):o.createElement(m,r({ref:t},u))}));function m(e,t){var a=arguments,i=t&&t.mdxType;if("string"==typeof e||i){var n=a.length,r=new Array(n);r[0]=h;var l={};for(var s in t)hasOwnProperty.call(t,s)&&(l[s]=t[s]);l.originalType=e,l[d]="string"==typeof e?e:i,r[1]=l;for(var p=2;p<n;p++)r[p]=a[p];return o.createElement.apply(null,r)}return o.createElement.apply(null,a)}h.displayName="MDXCreateElement"},2269:(e,t,a)=>{a.r(t),a.d(t,{assets:()=>s,contentTitle:()=>r,default:()=>d,frontMatter:()=>n,metadata:()=>l,toc:()=>p});var o=a(7462),i=(a(7294),a(3905));const n={sidebar_position:2,title:"What Are We Doing?"},r=void 0,l={unversionedId:"WHAT_ARE_WE_DOING",id:"WHAT_ARE_WE_DOING",title:"What Are We Doing?",description:"KMMBridge and it's reference workflow are doing several things that may seem confusing, but they're necessary to publish Kotlin Xcode Frameworks for iOS (and Apple targets generally). To better understand how to use KMMBridge, it'll be useful to explain what it needs to do and why.",source:"@site/docs/WHAT_ARE_WE_DOING.md",sourceDirName:".",slug:"/WHAT_ARE_WE_DOING",permalink:"/docs/WHAT_ARE_WE_DOING",draft:!1,editUrl:"https://github.com/touchlab/KMMBridge/tree/main/website/docs/WHAT_ARE_WE_DOING.md",tags:[],version:"current",lastUpdatedBy:"Kevin Galligan",lastUpdatedAt:1732381142,formattedLastUpdatedAt:"Nov 23, 2024",sidebarPosition:2,frontMatter:{sidebar_position:2,title:"What Are We Doing?"},sidebar:"tutorialSidebar",previous:{title:"KMMBridge Intro",permalink:"/docs/"},next:{title:"Default GitHub Workflow",permalink:"/docs/DEFAULT_GITHUB_FLOW"}},s={},p=[{value:"The Main Bundle",id:"the-main-bundle",level:2},{value:"Deploying The Bundle",id:"deploying-the-bundle",level:2},{value:"Artifact Managers",id:"artifact-managers",level:3},{value:"Publishing To Maven",id:"publishing-to-maven",level:3},{value:"Xcode Dependency Managers",id:"xcode-dependency-managers",level:2},{value:"SPM",id:"spm",level:3},{value:"SPM and KMMBridge builds",id:"spm-and-kmmbridge-builds",level:4},{value:"CocoaPods",id:"cocoapods",level:3},{value:"Private Repos",id:"private-repos",level:2},{value:"Next Steps",id:"next-steps",level:2},{value:"Get Started",id:"get-started",level:3},{value:"Deep(er) Dive",id:"deeper-dive",level:3}],u={toc:p};function d(e){let{components:t,...n}=e;return(0,i.kt)("wrapper",(0,o.Z)({},u,n,{components:t,mdxType:"MDXLayout"}),(0,i.kt)("p",null,"KMMBridge and it's reference workflow are doing several things that may seem confusing, but they're necessary to publish Kotlin Xcode Frameworks for iOS (and Apple targets generally). To better understand how to use KMMBridge, it'll be useful to explain what it needs to do and why."),(0,i.kt)("h2",{id:"the-main-bundle"},"The Main Bundle"),(0,i.kt)("p",null,"When building Kotlin for Xcode, the Kotlin compiler builds an Xcode Framework. The Framework is Xcode's library format. For the most part, when using code from Swift or Objective-C Frameworks are distributed with their source code and built on the machine that is using them. However, a Framework can instead have pre-built binaries. Because Kotlin has an entirely different build toolchain, the Kotlin compiler creates Xcode Frameworks as pre-built binaries."),(0,i.kt)("p",null,"The default Kotlin tooling is designed to create Xcode Frameworks for local use. However, for a number of reasons, you may want to package these Frameworks in a way that will allow you to share them with other developers. That's what KMMBridge does."),(0,i.kt)("p",null,"To package them, we want to take the Xcode Frameworks that the Kotlin compiler makes, do a slight format upgrade to something called an XCFramework (this is automatic), then collect all of the Frameworks into a zip file. That zip file can then easily be shared with other Xcode developers, and Xcode knows how to open and use it."),(0,i.kt)("admonition",{type:"info"},(0,i.kt)("p",{parentName:"admonition"},"There are generally multiple Framework files in your bundle, even if you're only publishing one library. On the JVM, there is usually just one jar output, but for Xcode, we need to build a binary for each target architecture. That can be many, but for iPhone app development, it is usually at least 2 and often 3. 2 simulators, for M1 and Intel Mac desktops, and arm64 for actual iPhone devices. The zip file contains all of these.")),(0,i.kt)("p",null,"Once configured, you can create this zip file by running the KMMBridge Gradle task:"),(0,i.kt)("pre",null,(0,i.kt)("code",{parentName:"pre",className:"language-shell"},"./gradlew zipXCFramework\n")),(0,i.kt)("h2",{id:"deploying-the-bundle"},"Deploying The Bundle"),(0,i.kt)("p",null,"To be able to access our bundle, we need to put it somewhere on the internet. Before we get into the details of various package managers, it's a good idea to understand this part."),(0,i.kt)("p",null,(0,i.kt)("strong",{parentName:"p"},"The URL where we host the zip file can be anywhere")),(0,i.kt)("p",null,"As long as the file is accessible to your choice of package manager, it can be hosted anywhere. They don't need to be in the same place. As far as SPM or CocoaPods are concerned, they get a URL which should return a zip file with some XCFrameworks in it."),(0,i.kt)("p",null,"So, you can put this binary anywhere, but again, ",(0,i.kt)("em",{parentName:"p"},"as long as the file is accessible to your choice of package manager"),"."),(0,i.kt)("p",null,"Once configured, you can upload this bundle with:"),(0,i.kt)("pre",null,(0,i.kt)("code",{parentName:"pre",className:"language-shell"},"./gradle uploadXCFramework\n")),(0,i.kt)("admonition",{type:"tip"},(0,i.kt)("p",{parentName:"admonition"},"You usually would not call ",(0,i.kt)("inlineCode",{parentName:"p"},"uploadXCFramework")," directly, but that is the task that uploads the bundle. Generally that task is a dependency of the main publishing tasks.")),(0,i.kt)("h3",{id:"artifact-managers"},"Artifact Managers"),(0,i.kt)("p",null,"Uploading is handled by the ",(0,i.kt)("inlineCode",{parentName:"p"},"ArtifactManager"),". KMMBridge has a limited set included. There is one for AWS S3, which can be useful for fully public URLs, although configuring private access is tricky."),(0,i.kt)("p",null,"The main artifact manager is for maven repos."),(0,i.kt)("h3",{id:"publishing-to-maven"},"Publishing To Maven"),(0,i.kt)("p",null,"This concept is a bit confusing at first. We aren't using a maven repo to store dependency information for Xcode. ",(0,i.kt)("strong",{parentName:"p"},'We are only using the maven repo as a standard place to "park" our zip file'),"."),(0,i.kt)("p",null,"After upload, we get the full URL pointing directly at that artifact, and that's what we use for our publishing configuration in later steps."),(0,i.kt)("p",null,"For example, in a sample app, we host our binaries in GitHub Packages, in a maven repo. ",(0,i.kt)("a",{parentName:"p",href:"https://github.com/touchlab-lab/KMMBridgeKickStartSKIE/packages/1935323"},"Here is the location where we publish the XCFramework zip files"),"."),(0,i.kt)("p",null,'We don\'t care about, or use, any of the other "stuff" in that location. Just the actual zip file asset.'),(0,i.kt)("p",null,(0,i.kt)("img",{alt:"Maven Zip",src:a(4791).Z,width:"979",height:"844"})),(0,i.kt)("p",null,"When we're done publishing, the only thing we care about with regards to the maven repo is that url. In this case it's:"),(0,i.kt)("pre",null,(0,i.kt)("code",{parentName:"pre"},"https://maven.pkg.github.com/touchlab-lab/KMMBridgeKickStartSKIE/co/touchlab/kmmbridgekickstartskie/allshared-kmmbridge/0.4.9/allshared-kmmbridge-0.4.9.zip\n")),(0,i.kt)("p",null,"Providing a maven publisher allows us to use GitHub Packages, Artifactory, JetBrains Space, and many others."),(0,i.kt)("p",null,"Again, to summarize. We're only using maven in this case to store the zip file."),(0,i.kt)("h2",{id:"xcode-dependency-managers"},"Xcode Dependency Managers"),(0,i.kt)("p",null,"The history of Xcode dependency management will seem a bit messy compared to the JVM ecosystem. Manually moving Xcode Frameworks around, or using git submodules, was common practice for a long time. CocoaPods was the major automated, centralized dependency manager for years. Only recently did Swift Package Manager (SPM) arrive, but it has quickly overtaken CocoaPods, or soon will, as many teams are moving to it, or planning to do so."),(0,i.kt)("p",null,"KMMBridge supports SPM and CocoaPods. It does not support Carthage, as Carthage ",(0,i.kt)("a",{parentName:"p",href:"https://github.com/google/GoogleSignIn-iOS/issues/60"},"appears to be on the way out"),"."),(0,i.kt)("p",null,"CocoaPods and SPM work quite differently, and some of the way that KMMBridge works reflects those differences, so it is useful to have a basic understanding of the process."),(0,i.kt)("h3",{id:"spm"},"SPM"),(0,i.kt)("p",null,"SPM is configured with a swift file called ",(0,i.kt)("inlineCode",{parentName:"p"},"Package.swift"),". This file will define targets and tell you about them. In our case, it'll have the url of our zip file."),(0,i.kt)("p",null,"SPM has a convention to support package discovery and versioning. It is simple, although restrictive."),(0,i.kt)("ul",null,(0,i.kt)("li",{parentName:"ul"},"An SPM library lives in a git repo."),(0,i.kt)("li",{parentName:"ul"},"The ",(0,i.kt)("inlineCode",{parentName:"li"},"Package.swift")," file for that library lives at the root of that repo."),(0,i.kt)("li",{parentName:"ul"},"Versioning of the library is strict semantic versioning, ex ",(0,i.kt)("inlineCode",{parentName:"li"},"1.2.3"),"."),(0,i.kt)("li",{parentName:"ul"},"Versions are marked by git tags.")),(0,i.kt)("p",null,"It is not complicated. However, that means each library needs to be in it's own repo. It is possible to have multiple modules in a library, from SPM's perspective, but for our Kotlin builds, it's much simpler to consider the library a single entity per-repo."),(0,i.kt)("p",null,"It will be tempting to try to have the ",(0,i.kt)("inlineCode",{parentName:"p"},"Package.swift")," file somewhere else in the repo, but accessing this will be painful. It is simply easier to put the ",(0,i.kt)("inlineCode",{parentName:"p"},"Package.swift")," file in the root."),(0,i.kt)("admonition",{type:"info"},(0,i.kt)("p",{parentName:"admonition"},"There are probably SPM configurations with Kotlin with multiple modules that would make sense, but we haven't explored them much. If you have a use case, please ",(0,i.kt)("a",{parentName:"p",href:"https://touchlab.co/keepintouch"},"reach out"),".")),(0,i.kt)("p",null,"So, when publishing a Kotlin binary for SPM, this is what needs to happen:"),(0,i.kt)("ul",null,(0,i.kt)("li",{parentName:"ul"},"Upload the XCFramework zip and get the url."),(0,i.kt)("li",{parentName:"ul"},"Create the ",(0,i.kt)("inlineCode",{parentName:"li"},"Package.swift")," file and write it to the repo root, pointing at that zip file."),(0,i.kt)("li",{parentName:"ul"},"Commit that ",(0,i.kt)("inlineCode",{parentName:"li"},"Package.swift")," file to the repo."),(0,i.kt)("li",{parentName:"ul"},"Tag it with the version for that build.")),(0,i.kt)("p",null,"Xcode will use the tag version info and the associated ",(0,i.kt)("inlineCode",{parentName:"p"},"Package.swift")," to grab the zip and use it."),(0,i.kt)("h4",{id:"spm-and-kmmbridge-builds"},"SPM and KMMBridge builds"),(0,i.kt)("p",null,"When using our ",(0,i.kt)("a",{parentName:"p",href:"https://github.com/touchlab/KMMBridgeGithubWorkflow"},"GitHub Workflow"),", note that we are creating branches during this build process. This is because the SPM publishing process needs to change your git repo, to update ",(0,i.kt)("inlineCode",{parentName:"p"},"Package.swift")," and add a tag. If you are using ",(0,i.kt)("a",{parentName:"p",href:"/docs/spm/IOS_LOCAL_DEV_SPM"},"SPM for local development"),", changing the ",(0,i.kt)("inlineCode",{parentName:"p"},"Package.swift")," is a problem. For local dev, ",(0,i.kt)("inlineCode",{parentName:"p"},"Package.swift")," points at the XCFramework in your local build folder."),(0,i.kt)("p",null,"For published versions, the only real difference is the ",(0,i.kt)("inlineCode",{parentName:"p"},"Package.swift"),". Our GitHub Workflow will created a build branch, add and commit the ",(0,i.kt)("inlineCode",{parentName:"p"},"Package.swift")," file, tag that version, then (by default) delete that branch. That will allow you to access the versioned commit, but won't keep a bunch of branches around."),(0,i.kt)("p",null,"You don't need to create branches, but adding release commits to your main branch can get a little busy, so we generally prefer the headless tagged commit approach."),(0,i.kt)("admonition",{type:"caution"},(0,i.kt)("mdxAdmonitionTitle",{parentName:"admonition"},"Where's the ",(0,i.kt)("inlineCode",{parentName:"mdxAdmonitionTitle"},"Package.swift")," file?!"),(0,i.kt)("p",{parentName:"admonition"},"KMMBridge generates the ",(0,i.kt)("inlineCode",{parentName:"p"},"Package.swift")," in the build branch when publishing. It'll generate ",(0,i.kt)("inlineCode",{parentName:"p"},"Package.swift")," in whatever branch you are in locally if you run the local build Gradle task. Otherwise, there either will not be a ",(0,i.kt)("inlineCode",{parentName:"p"},"Package.swift"),", or you'll need to create it.")),(0,i.kt)("h3",{id:"cocoapods"},"CocoaPods"),(0,i.kt)("p",null,"As mentioned, CocoaPods is very different than SPM in how it manages versions. Conceptually, it is more flexible, and doesn't clutter up your repo. However, it is significantly more complex to set up, and because the versions aren't in your repo, you'll lose the connection from source to version (unless you tag or similar)."),(0,i.kt)("p",null,"A CocoaPod version repo is just a git repo that holds CocoaPod spec files. ",(0,i.kt)("strong",{parentName:"p"},"It does not host the binary!!!")),(0,i.kt)("p",null,"The CocoaPod version repo is a ",(0,i.kt)("em",{parentName:"p"},"separate")," git repo. If an organization had a bunch of private CocoaPod libraries, they'd all be registered in a single Podspec repo. The main CocoaPods library repo is also just that. A big git Podspec repo."),(0,i.kt)("p",null,"When you publish a Kotlin binary with CocoaPods, here is what happens:"),(0,i.kt)("ul",null,(0,i.kt)("li",{parentName:"ul"},"Upload the XCFramework zip and get the url."),(0,i.kt)("li",{parentName:"ul"},"Create a CocoaPods podspec file. It basically tells CocoaPods about this version of your library."),(0,i.kt)("li",{parentName:"ul"},"Call the process ",(0,i.kt)("inlineCode",{parentName:"li"},"pod repo push")," with your podspec file. This is a custom command line process provided by CocoaPods."),(0,i.kt)("li",{parentName:"ul"},(0,i.kt)("inlineCode",{parentName:"li"},"pod repo push")," will then attempt to validate your new version, and if successful, add that version to the Podspec repo.")),(0,i.kt)("p",null,"In theory, you don't need to do anything to the source repo when this happens. However, as mentioned, you should probably tag the source so you can see where the build came from."),(0,i.kt)("p",null,"Just FYI, ",(0,i.kt)("inlineCode",{parentName:"p"},"pod repo push")," can fail if everything isn't perfect. Generally speaking, once your setup is working, you'll be OK, but it can be picky."),(0,i.kt)("admonition",{type:"caution"},(0,i.kt)("p",{parentName:"admonition"},"If you use our GitHub Workflow, you'll notice that we mark the repo with a temporary tag first, then attempt the publishing process, then finally add a final tag and remove the temp tag. This is because some artifact managers need to publish before we can get a URL, but if the build verification fails, that file alreagy exists, and future attempts to publish will fail for that same version. So, if you attempt to publish ",(0,i.kt)("inlineCode",{parentName:"p"},"2.1.34")," and verification fails, the next attempt will be ",(0,i.kt)("inlineCode",{parentName:"p"},"2.1.35"),", because the binary for ",(0,i.kt)("inlineCode",{parentName:"p"},"2.1.34")," was uploaded and attempting to replace it will fail.  It is unfortuanate, but again, once set up correctly, failures should be rare.")),(0,i.kt)("h2",{id:"private-repos"},"Private Repos"),(0,i.kt)("p",null,"Both SPM and CocoaPods allow you to use private storage for your builds. Access configuration differs between the two, but is generally well documented."),(0,i.kt)("p",null,"If you are using GitHub, our default configurations make managing authentication through GitHub straightforward."),(0,i.kt)("h2",{id:"next-steps"},"Next Steps"),(0,i.kt)("p",null,"That's the overview of what the publishing process is like. Next steps depend on your goals:"),(0,i.kt)("h3",{id:"get-started"},"Get Started"),(0,i.kt)("p",null,'If you want to get started and like to try some "hands-on" code, check out ',(0,i.kt)("a",{parentName:"p",href:"https://touchlab.co/kmmbridge-quickstart-updates"},"KMMBridge Quick Start Updates"),". There's a template project and a lot more detail on setting and publishing Kotlin for native mobile teams."),(0,i.kt)("h3",{id:"deeper-dive"},"Deep(er) Dive"),(0,i.kt)("p",null,"To continue down the documentation path, check out the next doc: ",(0,i.kt)("a",{parentName:"p",href:"/docs/DEFAULT_GITHUB_FLOW"},"Default GitHub Workflow"),", which discusses the details of our reference build workflow, and possible configuration options."))}d.isMDXComponent=!0},4791:(e,t,a)=>{a.d(t,{Z:()=>o});const o=a.p+"assets/images/mavenasset-70d52c7d53465bf412421b88912fb986.png"}}]);