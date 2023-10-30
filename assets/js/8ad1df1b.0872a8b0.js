"use strict";(self.webpackChunktouchlab=self.webpackChunktouchlab||[]).push([[9703],{3905:(e,o,t)=>{t.d(o,{Zo:()=>p,kt:()=>O});var n=t(7294);function r(e,o,t){return o in e?Object.defineProperty(e,o,{value:t,enumerable:!0,configurable:!0,writable:!0}):e[o]=t,e}function a(e,o){var t=Object.keys(e);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(e);o&&(n=n.filter((function(o){return Object.getOwnPropertyDescriptor(e,o).enumerable}))),t.push.apply(t,n)}return t}function l(e){for(var o=1;o<arguments.length;o++){var t=null!=arguments[o]?arguments[o]:{};o%2?a(Object(t),!0).forEach((function(o){r(e,o,t[o])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(t)):a(Object(t)).forEach((function(o){Object.defineProperty(e,o,Object.getOwnPropertyDescriptor(t,o))}))}return e}function c(e,o){if(null==e)return{};var t,n,r=function(e,o){if(null==e)return{};var t,n,r={},a=Object.keys(e);for(n=0;n<a.length;n++)t=a[n],o.indexOf(t)>=0||(r[t]=e[t]);return r}(e,o);if(Object.getOwnPropertySymbols){var a=Object.getOwnPropertySymbols(e);for(n=0;n<a.length;n++)t=a[n],o.indexOf(t)>=0||Object.prototype.propertyIsEnumerable.call(e,t)&&(r[t]=e[t])}return r}var i=n.createContext({}),d=function(e){var o=n.useContext(i),t=o;return e&&(t="function"==typeof e?e(o):l(l({},o),e)),t},p=function(e){var o=d(e.components);return n.createElement(i.Provider,{value:o},e.children)},u="mdxType",s={inlineCode:"code",wrapper:function(e){var o=e.children;return n.createElement(n.Fragment,{},o)}},m=n.forwardRef((function(e,o){var t=e.components,r=e.mdxType,a=e.originalType,i=e.parentName,p=c(e,["components","mdxType","originalType","parentName"]),u=d(t),m=r,O=u["".concat(i,".").concat(m)]||u[m]||s[m]||a;return t?n.createElement(O,l(l({ref:o},p),{},{components:t})):n.createElement(O,l({ref:o},p))}));function O(e,o){var t=arguments,r=o&&o.mdxType;if("string"==typeof e||r){var a=t.length,l=new Array(a);l[0]=m;var c={};for(var i in o)hasOwnProperty.call(o,i)&&(c[i]=o[i]);c.originalType=e,c[u]="string"==typeof e?e:r,l[1]=c;for(var d=2;d<a;d++)l[d]=t[d];return n.createElement.apply(null,l)}return n.createElement.apply(null,t)}m.displayName="MDXCreateElement"},5682:(e,o,t)=>{t.r(o),t.d(o,{assets:()=>i,contentTitle:()=>l,default:()=>u,frontMatter:()=>a,metadata:()=>c,toc:()=>d});var n=t(7462),r=(t(7294),t(3905));const a={},l="CocoaPods Local Dev Flow",c={unversionedId:"cocoapods/IOS_LOCAL_DEV_COCOAPODS",id:"cocoapods/IOS_LOCAL_DEV_COCOAPODS",title:"CocoaPods Local Dev Flow",description:"After you have integrated CocoaPods into Xcode, you can run local dev builds by adding the Kotlin code as a local dev Cocoapod.",source:"@site/docs/cocoapods/02_IOS_LOCAL_DEV_COCOAPODS.md",sourceDirName:"cocoapods",slug:"/cocoapods/IOS_LOCAL_DEV_COCOAPODS",permalink:"/docs/cocoapods/IOS_LOCAL_DEV_COCOAPODS",draft:!1,editUrl:"https://github.com/touchlab/KMMBridge/tree/main/website/docs/cocoapods/02_IOS_LOCAL_DEV_COCOAPODS.md",tags:[],version:"current",lastUpdatedBy:"Kevin Galligan",lastUpdatedAt:1698631967,formattedLastUpdatedAt:"Oct 30, 2023",sidebarPosition:2,frontMatter:{},sidebar:"tutorialSidebar",previous:{title:"Using CocoaPods",permalink:"/docs/cocoapods/IOS_COCOAPODS"},next:{title:"Publishing Podspecs to GitHub",permalink:"/docs/cocoapods/COCOAPODS_GITHUB_PODSPEC"}},i={},d=[],p={toc:d};function u(e){let{components:o,...t}=e;return(0,r.kt)("wrapper",(0,n.Z)({},p,t,{components:o,mdxType:"MDXLayout"}),(0,r.kt)("h1",{id:"cocoapods-local-dev-flow"},"CocoaPods Local Dev Flow"),(0,r.kt)("p",null,"After you have ",(0,r.kt)("a",{parentName:"p",href:"/docs/cocoapods/IOS_COCOAPODS"},"integrated CocoaPods into Xcode"),", you can run local dev builds by adding the Kotlin code as a local dev Cocoapod."),(0,r.kt)("blockquote",null,(0,r.kt)("p",{parentName:"blockquote"},"For this example, we will assume you have cloned both the Xcode repo and the Kotln repo to the same directory, although you can clone them anywhere on your local drive. Just replace the path accordingly.")),(0,r.kt)("p",null,"Modify your ",(0,r.kt)("inlineCode",{parentName:"p"},"Podfile")," to check environment variables for a local dev path."),(0,r.kt)("pre",null,(0,r.kt)("code",{parentName:"pre",className:"language-ruby"},"platform :ios, '13'\n\nsource 'https://github.com/touchlab/PublicPodspecs.git'\n\ntarget 'KMMBridgeSampleCocoaPods' do\n  if ENV.include?(\"LOCAL_KOTLIN_PATH\")\n    pod 'shared', :path => ENV[\"LOCAL_KOTLIN_PATH\"]\n  else\n    pod 'shared', '0.2.1'\n  end\nend\n")),(0,r.kt)("p",null,"If you run ",(0,r.kt)("inlineCode",{parentName:"p"},"pod install")," without any environment variable changes, you will get the prebuilt binary. However, you can use the local binary by running:"),(0,r.kt)("pre",null,(0,r.kt)("code",{parentName:"pre",className:"language-shell"},"export LOCAL_KOTLIN_PATH=../KMMBridgeSampleKotlin/shared\npod install\n")),(0,r.kt)("p",null,"In our example, the Kotlin project is called ",(0,r.kt)("inlineCode",{parentName:"p"},"KMMBridgeSampleKotlin")," and the Kotlin code is in a module called ",(0,r.kt)("inlineCode",{parentName:"p"},"shared"),". Replace with your project and module names."),(0,r.kt)("p",null,"After running ",(0,r.kt)("inlineCode",{parentName:"p"},"pod install"),", close and reopen the ",(0,r.kt)("inlineCode",{parentName:"p"},"xcworkspace")," file. You should now be in the standard Kotlin local CocoaPods build flow."),(0,r.kt)("blockquote",null,(0,r.kt)("p",{parentName:"blockquote"},"Note: It is ",(0,r.kt)("em",{parentName:"p"},"highly")," recommended that you run ",(0,r.kt)("inlineCode",{parentName:"p"},"linkPodDebugFrameworkIosX64")," or ",(0,r.kt)("inlineCode",{parentName:"p"},"linkPodDebugFrameworkIosSimulatorArm64"),", depending on your Mac architecture, before you run ",(0,r.kt)("inlineCode",{parentName:"p"},"pod install"),", due to a minor issue with the Kotlin CocoaPods integration.")),(0,r.kt)("p",null,"Once your changes are complete, push them to your repo and run the KMMBridge build process again. When complete, you should be able to remove the local dev flow by removing the environment variable and running ",(0,r.kt)("inlineCode",{parentName:"p"},"pod install")," again."))}u.isMDXComponent=!0}}]);