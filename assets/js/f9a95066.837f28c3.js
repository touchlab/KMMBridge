"use strict";(self.webpackChunktouchlab=self.webpackChunktouchlab||[]).push([[3317],{3905:(e,t,o)=>{o.d(t,{Zo:()=>s,kt:()=>h});var a=o(7294);function n(e,t,o){return t in e?Object.defineProperty(e,t,{value:o,enumerable:!0,configurable:!0,writable:!0}):e[t]=o,e}function r(e,t){var o=Object.keys(e);if(Object.getOwnPropertySymbols){var a=Object.getOwnPropertySymbols(e);t&&(a=a.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),o.push.apply(o,a)}return o}function l(e){for(var t=1;t<arguments.length;t++){var o=null!=arguments[t]?arguments[t]:{};t%2?r(Object(o),!0).forEach((function(t){n(e,t,o[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(o)):r(Object(o)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(o,t))}))}return e}function i(e,t){if(null==e)return{};var o,a,n=function(e,t){if(null==e)return{};var o,a,n={},r=Object.keys(e);for(a=0;a<r.length;a++)o=r[a],t.indexOf(o)>=0||(n[o]=e[o]);return n}(e,t);if(Object.getOwnPropertySymbols){var r=Object.getOwnPropertySymbols(e);for(a=0;a<r.length;a++)o=r[a],t.indexOf(o)>=0||Object.prototype.propertyIsEnumerable.call(e,o)&&(n[o]=e[o])}return n}var c=a.createContext({}),p=function(e){var t=a.useContext(c),o=t;return e&&(o="function"==typeof e?e(t):l(l({},t),e)),o},s=function(e){var t=p(e.components);return a.createElement(c.Provider,{value:t},e.children)},d="mdxType",u={inlineCode:"code",wrapper:function(e){var t=e.children;return a.createElement(a.Fragment,{},t)}},m=a.forwardRef((function(e,t){var o=e.components,n=e.mdxType,r=e.originalType,c=e.parentName,s=i(e,["components","mdxType","originalType","parentName"]),d=p(o),m=n,h=d["".concat(c,".").concat(m)]||d[m]||u[m]||r;return o?a.createElement(h,l(l({ref:t},s),{},{components:o})):a.createElement(h,l({ref:t},s))}));function h(e,t){var o=arguments,n=t&&t.mdxType;if("string"==typeof e||n){var r=o.length,l=new Array(r);l[0]=m;var i={};for(var c in t)hasOwnProperty.call(t,c)&&(i[c]=t[c]);i.originalType=e,i[d]="string"==typeof e?e:n,l[1]=i;for(var p=2;p<r;p++)l[p]=o[p];return a.createElement.apply(null,l)}return a.createElement.apply(null,o)}m.displayName="MDXCreateElement"},1631:(e,t,o)=>{o.r(t),o.d(t,{assets:()=>c,contentTitle:()=>l,default:()=>d,frontMatter:()=>r,metadata:()=>i,toc:()=>p});var a=o(7462),n=(o(7294),o(3905));const r={},l="Using CocoaPods",i={unversionedId:"cocoapods/IOS_COCOAPODS",id:"version-0.3.x/cocoapods/IOS_COCOAPODS",title:"Using CocoaPods",description:"You'll need an Xcode project with CocoaPods set up. That means you'll have a Podfile that you can edit with the necessary code to integrate your Kotlin module.",source:"@site/versioned_docs/version-0.3.x/cocoapods/01_IOS_COCOAPODS.md",sourceDirName:"cocoapods",slug:"/cocoapods/IOS_COCOAPODS",permalink:"/docs/0.3.x/cocoapods/IOS_COCOAPODS",draft:!1,editUrl:"https://github.com/touchlab/KMMBridge/tree/main/website/versioned_docs/version-0.3.x/cocoapods/01_IOS_COCOAPODS.md",tags:[],version:"0.3.x",lastUpdatedBy:"Jigar Brahmbhatt",lastUpdatedAt:1706116862,formattedLastUpdatedAt:"Jan 24, 2024",sidebarPosition:1,frontMatter:{},sidebar:"tutorialSidebar",previous:{title:"CocoaPods",permalink:"/docs/0.3.x/category/cocoapods"},next:{title:"CocoaPods Local Dev Flow",permalink:"/docs/0.3.x/cocoapods/IOS_LOCAL_DEV_COCOAPODS"}},c={},p=[{value:"Artifact Authentication",id:"artifact-authentication",level:2},{value:"Add Podspec Repo",id:"add-podspec-repo",level:2},{value:"Local Kotlin Dev",id:"local-kotlin-dev",level:2}],s={toc:p};function d(e){let{components:t,...o}=e;return(0,n.kt)("wrapper",(0,a.Z)({},s,o,{components:t,mdxType:"MDXLayout"}),(0,n.kt)("h1",{id:"using-cocoapods"},"Using CocoaPods"),(0,n.kt)("p",null,"You'll need an Xcode project with CocoaPods set up. That means you'll have a ",(0,n.kt)("inlineCode",{parentName:"p"},"Podfile")," that you can edit with the necessary code to integrate your Kotlin module."),(0,n.kt)("h2",{id:"artifact-authentication"},"Artifact Authentication"),(0,n.kt)("p",null,"For artifacts that are kept in private storage, you may need to add authentication information so your ",(0,n.kt)("inlineCode",{parentName:"p"},"~/.netrc")," file or your Mac's Keychain Access. See ",(0,n.kt)("a",{parentName:"p",href:"/docs/0.3.x/DEFAULT_GITHUB_FLOW#private-repos"},"the section here")," for a description of how to set up private file access."),(0,n.kt)("h2",{id:"add-podspec-repo"},"Add Podspec Repo"),(0,n.kt)("p",null,"In your ",(0,n.kt)("inlineCode",{parentName:"p"},"Podfile"),", add the module and the source. An example ",(0,n.kt)("inlineCode",{parentName:"p"},"Podfile")," might look like this:"),(0,n.kt)("pre",null,(0,n.kt)("code",{parentName:"pre",className:"language-ruby"},"platform :ios, '13'\n\nsource 'https://github.com/touchlab/PublicPodspecs.git'\n\ntarget 'KMMBridgeSampleCocoaPods' do\n  pod 'shared', '0.2.1'\nend\n\n")),(0,n.kt)("p",null,"Then, to initialize CocoaPods, run:"),(0,n.kt)("pre",null,(0,n.kt)("code",{parentName:"pre",className:"language-shell"},"pod install\n")),(0,n.kt)("p",null,"As you publish new versions of the library, you will need to update the local podspec repo copy. Either run:"),(0,n.kt)("pre",null,(0,n.kt)("code",{parentName:"pre",className:"language-shell"},"pod repo update\n")),(0,n.kt)("p",null,"Or update the podspec when you're updating your CocoaPods project:"),(0,n.kt)("pre",null,(0,n.kt)("code",{parentName:"pre",className:"language-shell"},"pod install --repo-update\n# Or...\npod update --repo-update\n")),(0,n.kt)("p",null,"Assuming that all worked, you should be able to open the project and build it."),(0,n.kt)("p",null,"If you are using a private podspec repo, your setup should work if you've added the authentication above. If the files can't be synced, make sure to double-check the auth setup."),(0,n.kt)("p",null,(0,n.kt)("strong",{parentName:"p"},(0,n.kt)("em",{parentName:"strong"},"VERY IMPORTANT!!!"))),(0,n.kt)("p",null,"After you run ",(0,n.kt)("inlineCode",{parentName:"p"},"pod install"),", CocoaPods generates an ",(0,n.kt)("inlineCode",{parentName:"p"},"xcworkspace")," file. There is usually both an ",(0,n.kt)("inlineCode",{parentName:"p"},"xcodeproj")," and an ",(0,n.kt)("inlineCode",{parentName:"p"},"xcworkspace"),". Make sure you open the ",(0,n.kt)("inlineCode",{parentName:"p"},"xcworkspace")," file!!!"),(0,n.kt)("p",null,(0,n.kt)("img",{parentName:"p",src:"https://tl-navigator-images.s3.us-east-1.amazonaws.com/docimages/2022-10-06_09-11-xcworkspacefile.png",alt:"xcworkspacefile"})),(0,n.kt)("h2",{id:"local-kotlin-dev"},"Local Kotlin Dev"),(0,n.kt)("p",null,"If you are editing Kotlin, you will probably want to test it locally. To do that, see  ",(0,n.kt)("a",{parentName:"p",href:"/docs/0.3.x/cocoapods/IOS_LOCAL_DEV_COCOAPODS"},"IOS_LOCAL_DEV_COCOAPODS"),"."))}d.isMDXComponent=!0}}]);