"use strict";(self.webpackChunkmy_website_ts=self.webpackChunkmy_website_ts||[]).push([[229],{3905:function(e,n,t){t.d(n,{Zo:function(){return p},kt:function(){return m}});var o=t(7294);function a(e,n,t){return n in e?Object.defineProperty(e,n,{value:t,enumerable:!0,configurable:!0,writable:!0}):e[n]=t,e}function r(e,n){var t=Object.keys(e);if(Object.getOwnPropertySymbols){var o=Object.getOwnPropertySymbols(e);n&&(o=o.filter((function(n){return Object.getOwnPropertyDescriptor(e,n).enumerable}))),t.push.apply(t,o)}return t}function i(e){for(var n=1;n<arguments.length;n++){var t=null!=arguments[n]?arguments[n]:{};n%2?r(Object(t),!0).forEach((function(n){a(e,n,t[n])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(t)):r(Object(t)).forEach((function(n){Object.defineProperty(e,n,Object.getOwnPropertyDescriptor(t,n))}))}return e}function s(e,n){if(null==e)return{};var t,o,a=function(e,n){if(null==e)return{};var t,o,a={},r=Object.keys(e);for(o=0;o<r.length;o++)t=r[o],n.indexOf(t)>=0||(a[t]=e[t]);return a}(e,n);if(Object.getOwnPropertySymbols){var r=Object.getOwnPropertySymbols(e);for(o=0;o<r.length;o++)t=r[o],n.indexOf(t)>=0||Object.prototype.propertyIsEnumerable.call(e,t)&&(a[t]=e[t])}return a}var l=o.createContext({}),c=function(e){var n=o.useContext(l),t=n;return e&&(t="function"==typeof e?e(n):i(i({},n),e)),t},p=function(e){var n=c(e.components);return o.createElement(l.Provider,{value:n},e.children)},u={inlineCode:"code",wrapper:function(e){var n=e.children;return o.createElement(o.Fragment,{},n)}},d=o.forwardRef((function(e,n){var t=e.components,a=e.mdxType,r=e.originalType,l=e.parentName,p=s(e,["components","mdxType","originalType","parentName"]),d=c(t),m=a,h=d["".concat(l,".").concat(m)]||d[m]||u[m]||r;return t?o.createElement(h,i(i({ref:n},p),{},{components:t})):o.createElement(h,i({ref:n},p))}));function m(e,n){var t=arguments,a=n&&n.mdxType;if("string"==typeof e||a){var r=t.length,i=new Array(r);i[0]=d;var s={};for(var l in n)hasOwnProperty.call(n,l)&&(s[l]=n[l]);s.originalType=e,s.mdxType="string"==typeof e?e:a,i[1]=s;for(var c=2;c<r;c++)i[c]=t[c];return o.createElement.apply(null,i)}return o.createElement.apply(null,t)}d.displayName="MDXCreateElement"},9144:function(e,n,t){t.r(n),t.d(n,{assets:function(){return p},contentTitle:function(){return l},default:function(){return m},frontMatter:function(){return s},metadata:function(){return c},toc:function(){return u}});var o=t(7462),a=t(3366),r=(t(7294),t(3905)),i=["components"],s={},l=void 0,c={unversionedId:"cocoapods/Cocoapods",id:"cocoapods/Cocoapods",title:"Cocoapods",description:"Basic Configuration",source:"@site/docs/cocoapods/Cocoapods.md",sourceDirName:"cocoapods",slug:"/cocoapods/",permalink:"/KMMBridge/docs/cocoapods/",draft:!1,editUrl:"https://github.com/touchlab/KMMBridge/tree/main/website/docs/cocoapods/Cocoapods.md",tags:[],version:"current",lastUpdatedBy:"Kevin Galligan",lastUpdatedAt:1665362437,formattedLastUpdatedAt:"10/10/2022",frontMatter:{},sidebar:"tutorialSidebar",previous:{title:"Publishing Podspecs to GitHub",permalink:"/KMMBridge/docs/cocoapods/COCOAPODS_GITHUB_PODSPEC"},next:{title:"Swift Package Manager (SPM)",permalink:"/KMMBridge/docs/category/swift-package-manager-spm"}},p={},u=[{value:"Basic Configuration",id:"basic-configuration",level:2},{value:"Publishing a new version",id:"publishing-a-new-version",level:2},{value:"Version numbering",id:"version-numbering",level:3},{value:"Local Development",id:"local-development",level:2},{value:"Uploading to a custom S3 bucket",id:"uploading-to-a-custom-s3-bucket",level:2}],d={toc:u};function m(e){var n=e.components,t=(0,a.Z)(e,i);return(0,r.kt)("wrapper",(0,o.Z)({},d,t,{components:n,mdxType:"MDXLayout"}),(0,r.kt)("h2",{id:"basic-configuration"},"Basic Configuration"),(0,r.kt)("p",null,"Add the sonatype snapshots repo where the plugin currently lives"),(0,r.kt)("pre",null,(0,r.kt)("code",{parentName:"pre",className:"language-kotlin"},'// settings.gradle.kts\npluginManagement {\n    repositories {\n        ...\n        maven("https://oss.sonatype.org/content/repositories/snapshots")\n    }\n}\n')),(0,r.kt)("p",null,"Add the Faktory plugin to your project, in the same module where the Kotlin CocoaPods plugin is configured."),(0,r.kt)("pre",null,(0,r.kt)("code",{parentName:"pre",className:"language-kotlin"},'// build.gradle.kts\nplugins {\n    kotlin("multiplatform")\n    kotlin("native.cocoapods")\n    id("co.touchlab.faktory.kmmbridge") version "0.2.2"\n}\n')),(0,r.kt)("p",null,"Now configure Faktory for cocoapods"),(0,r.kt)("pre",null,(0,r.kt)("code",{parentName:"pre",className:"language-kotlin"},'kmmbridge {\n    cocoapods("git@github.com:your-org/Podspecs.git")\n}\n')),(0,r.kt)("p",null,"You'll need to provide a url to your own spec repo to host the podspec. For more on spec repos, see\nthe ",(0,r.kt)("a",{parentName:"p",href:"https://guides.cocoapods.org/making/private-cocoapods.html"},"CocoaPods documentation"),"."),(0,r.kt)("h2",{id:"publishing-a-new-version"},"Publishing a new version"),(0,r.kt)("p",null,"To publish a new version, use the ",(0,r.kt)("inlineCode",{parentName:"p"},"kmmBridgePublish")," gradle task.\nThis will take care of uploading a new binary to your artifact storage, as well as uploading the podspec to the designated\nspec repo so the library can be consumed from non-Kotlin-aware iOS projects."),(0,r.kt)("p",null,"The Podspec will be generated based on the same configuration used by the kotlin.cocoapods plugin, except that\nthe ",(0,r.kt)("inlineCode",{parentName:"p"},"source")," and ",(0,r.kt)("inlineCode",{parentName:"p"},"version")," will be overridden by the KMMBridge plugin."),(0,r.kt)("h3",{id:"version-numbering"},"Version numbering"),(0,r.kt)("p",null,"The KMMBridge plugin does some automated version management in order to ensure that new library versions are published\nwith a consistently increasing version number. A base version is set either in the ",(0,r.kt)("inlineCode",{parentName:"p"},"cocoapods")," block or directly on the\ngradle project, as managed by the kotlin.cocoapods plugin."),(0,r.kt)("pre",null,(0,r.kt)("code",{parentName:"pre",className:"language-kotlin"},'version = "1.2" // This will be used second, if not set in cocoapods block\n\nkotlin {\n    cocoapods {\n        version = "1.2" // This will be used first, if present\n    }\n}\n\n')),(0,r.kt)("p",null,"When publishing a new cocoapod, Faktory will append a timestamp to this version to ensure a unique and increasing\nversion number. This will result in a published version like ",(0,r.kt)("inlineCode",{parentName:"p"},"1.2.1663779589077"),". To maintain semver compatibility, you\nshould set the base version using only a major and minor version (1.2, rather than 1.2.3) so that the timestamp piece\nbecomes the patch version."),(0,r.kt)("p",null,"To consume this from your iOS project without needing to manually update versions, you can use\nCocoaPods ",(0,r.kt)("a",{parentName:"p",href:"https://guides.cocoapods.org/using/the-podfile.html#specifying-pod-versions"},"optimistic version operator")," as\nfollows:"),(0,r.kt)("pre",null,(0,r.kt)("code",{parentName:"pre",className:"language-ruby"},"pod 'MyShared', '~> 1.2'\n")),(0,r.kt)("p",null,"When you first run a ",(0,r.kt)("inlineCode",{parentName:"p"},"pod install")," with this line in your ",(0,r.kt)("inlineCode",{parentName:"p"},"Podfile"),", CocoaPods will use the most recent version\nmatching ",(0,r.kt)("inlineCode",{parentName:"p"},"1.2.x")," and generate a ",(0,r.kt)("inlineCode",{parentName:"p"},"Podfile.lock")," file. The next time you run ",(0,r.kt)("inlineCode",{parentName:"p"},"pod install")," it will read the version from\nthat lock file until the version in the ",(0,r.kt)("inlineCode",{parentName:"p"},"Podfile")," changes. When you publish a new ",(0,r.kt)("inlineCode",{parentName:"p"},"1.2.x")," version, you can update the\nlock file by running ",(0,r.kt)("inlineCode",{parentName:"p"},"pod update"),"."),(0,r.kt)("p",null,"Configuring things in this way makes it easier to manage integrating changes from shared code into the iOS project. If a\nbreaking change happens in the Kotlin code, you can publish it without impacting the iOS project until you\nrun ",(0,r.kt)("inlineCode",{parentName:"p"},"pod update")," when you're ready to incorporate the iOS-side changes."),(0,r.kt)("p",null,"If you prefer instead to always set an exact version, you can check the spec repo for the most recent published version\nand set it manually."),(0,r.kt)("pre",null,(0,r.kt)("code",{parentName:"pre",className:"language-ruby"},"pod 'MyShared', '1.2.1663779589077'\n")),(0,r.kt)("h2",{id:"local-development"},"Local Development"),(0,r.kt)("p",null,"While Faktory allows fast and easy builds on iOS using pre-built binaries, if you are doing work in the shared code, you\nwill want to be able to test your changes on iOS rather than using the pre-built binaries. To support this without\nmaking ",(0,r.kt)("inlineCode",{parentName:"p"},"Podfile")," changes which might accidentally get committed to source control, we recommend using an environment\nvariable to point to your local version."),(0,r.kt)("p",null,"You can use the variable in your ",(0,r.kt)("inlineCode",{parentName:"p"},"Podfile")," as follows:"),(0,r.kt)("pre",null,(0,r.kt)("code",{parentName:"pre",className:"language-ruby"},"target 'MyApp' do\n  if ENV.include?(\"LOCAL_KOTLIN_PATH\")\n    pod 'MyShared', :path => ENV[\"LOCAL_KOTLIN_PATH\"]\n  else\n    pod 'MyShared', '~> 1.2'\n  end\nend\n")),(0,r.kt)("p",null,"and setting it before running ",(0,r.kt)("inlineCode",{parentName:"p"},"pod install")),(0,r.kt)("pre",null,(0,r.kt)("code",{parentName:"pre",className:"language-sh"},'export LOCAL_KOTLIN_PATH="path/to/cloned/MyShared"\npod install\n')),(0,r.kt)("p",null,"and revert to using the remote:"),(0,r.kt)("pre",null,(0,r.kt)("code",{parentName:"pre",className:"language-sh"},"unset LOCAL_KOTLIN_PATH\npod install\n")),(0,r.kt)("p",null,"When pointing to a local version like this you will, of course, need the shared code cloned in the location specified\nand you will need the appropriate environment set up for building KMP"),(0,r.kt)("h2",{id:"uploading-to-a-custom-s3-bucket"},"Uploading to a custom S3 bucket"),(0,r.kt)("p",null,"By default, the Faktory plugin uploads binaries through Touchlab's servers. As an alternative, you can provide your own\nAmazon S3 bucket to use instead."),(0,r.kt)("pre",null,(0,r.kt)("code",{parentName:"pre",className:"language-kotlin"},'faktory {\n    // faktoryReadKey.set("0123456789ABCDEF") // This is no longer necessary\n    ...\n    s3Public(\n        region = "us-east-2",\n        bucket = "my-s3-bucket",\n        accessKeyId = "0123456789ABCDEF",\n        secretAccessKey = "0123456789ABCDEF",\n        makeArtifactsPublic = true, // Set this always to true\n        altBaseUrl = null // Set this always to null\n    )\n}\n')),(0,r.kt)("p",null,"In the current version of the plugin, the bucket must be configured for public access. This may change in the future, so\nif private S3 access is important to you, let us know! "),(0,r.kt)("p",null,"To configure an access key, go to\nthe ",(0,r.kt)("a",{parentName:"p",href:"https://console.aws.amazon.com/iam/home#/security_credentials"},"AWS IAM management console"),' and click "Create access\nkey". The "Access key ID" and "Secret access key" fields correspond to ',(0,r.kt)("inlineCode",{parentName:"p"},"accessKeyId")," and ",(0,r.kt)("inlineCode",{parentName:"p"},"secretAccessKey")," in the\nFaktory configuration."),(0,r.kt)("p",null,"These keys are only used when uploading a new binary, so you may want to read them from environment variables or some\nother source that's not checked into the project rather than committing them into the gradle file."))}m.isMDXComponent=!0}}]);