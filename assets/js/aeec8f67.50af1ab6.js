"use strict";(self.webpackChunkmy_website_ts=self.webpackChunkmy_website_ts||[]).push([[233],{3905:function(e,t,r){r.d(t,{Zo:function(){return d},kt:function(){return f}});var n=r(7294);function o(e,t,r){return t in e?Object.defineProperty(e,t,{value:r,enumerable:!0,configurable:!0,writable:!0}):e[t]=r,e}function i(e,t){var r=Object.keys(e);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(e);t&&(n=n.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),r.push.apply(r,n)}return r}function a(e){for(var t=1;t<arguments.length;t++){var r=null!=arguments[t]?arguments[t]:{};t%2?i(Object(r),!0).forEach((function(t){o(e,t,r[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(r)):i(Object(r)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(r,t))}))}return e}function c(e,t){if(null==e)return{};var r,n,o=function(e,t){if(null==e)return{};var r,n,o={},i=Object.keys(e);for(n=0;n<i.length;n++)r=i[n],t.indexOf(r)>=0||(o[r]=e[r]);return o}(e,t);if(Object.getOwnPropertySymbols){var i=Object.getOwnPropertySymbols(e);for(n=0;n<i.length;n++)r=i[n],t.indexOf(r)>=0||Object.prototype.propertyIsEnumerable.call(e,r)&&(o[r]=e[r])}return o}var s=n.createContext({}),u=function(e){var t=n.useContext(s),r=t;return e&&(r="function"==typeof e?e(t):a(a({},t),e)),r},d=function(e){var t=u(e.components);return n.createElement(s.Provider,{value:t},e.children)},l={inlineCode:"code",wrapper:function(e){var t=e.children;return n.createElement(n.Fragment,{},t)}},p=n.forwardRef((function(e,t){var r=e.components,o=e.mdxType,i=e.originalType,s=e.parentName,d=c(e,["components","mdxType","originalType","parentName"]),p=u(r),f=o,O=p["".concat(s,".").concat(f)]||p[f]||l[f]||i;return r?n.createElement(O,a(a({ref:t},d),{},{components:r})):n.createElement(O,a({ref:t},d))}));function f(e,t){var r=arguments,o=t&&t.mdxType;if("string"==typeof e||o){var i=r.length,a=new Array(i);a[0]=p;var c={};for(var s in t)hasOwnProperty.call(t,s)&&(c[s]=t[s]);c.originalType=e,c.mdxType="string"==typeof e?e:o,a[1]=c;for(var u=2;u<i;u++)a[u]=r[u];return n.createElement.apply(null,a)}return n.createElement.apply(null,r)}p.displayName="MDXCreateElement"},7285:function(e,t,r){r.r(t),r.d(t,{assets:function(){return d},contentTitle:function(){return s},default:function(){return f},frontMatter:function(){return c},metadata:function(){return u},toc:function(){return l}});var n=r(7462),o=r(3366),i=(r(7294),r(3905)),a=["components"],c={},s="iOS Dev Setup",u={unversionedId:"docs/IOS_DEV_SETUP",id:"docs/IOS_DEV_SETUP",title:"iOS Dev Setup",description:"To use the published Xcode Framework, you'll need to integrate it into your Xcode project. You'll also need to understand how to add authentication information, if required by your artifact storage.",source:"@site/docs/docs/IOS_DEV_SETUP.md",sourceDirName:"docs",slug:"/docs/IOS_DEV_SETUP",permalink:"/KMMBridge/docs/docs/IOS_DEV_SETUP",draft:!1,editUrl:"https://github.com/touchlab/KMMBridge/tree/main/website/docs/docs/IOS_DEV_SETUP.md",tags:[],version:"current",lastUpdatedBy:"Kevin Galligan",lastUpdatedAt:1665329918,formattedLastUpdatedAt:"10/9/2022",frontMatter:{},sidebar:"tutorialSidebar",previous:{title:"Using Cocoapods",permalink:"/KMMBridge/docs/docs/IOS_COCOAPODS"},next:{title:"Cocoapods Local Dev Flow",permalink:"/KMMBridge/docs/docs/IOS_LOCAL_DEV_COCOAPODS"}},d={},l=[{value:"Private Github Releases",id:"private-github-releases",level:2},{value:"Using Cocoapods",id:"using-cocoapods",level:2},{value:"Using SPM",id:"using-spm",level:2}],p={toc:l};function f(e){var t=e.components,r=(0,o.Z)(e,a);return(0,i.kt)("wrapper",(0,n.Z)({},p,r,{components:t,mdxType:"MDXLayout"}),(0,i.kt)("h1",{id:"ios-dev-setup"},"iOS Dev Setup"),(0,i.kt)("p",null,"To use the published Xcode Framework, you'll need to integrate it into your Xcode project. You'll also need to understand how to add authentication information, if required by your artifact storage."),(0,i.kt)("p",null,"For developers editing Kotlin, you will want to test locally-built Kotlin code directly in your Xcode project from time to time. How that works differs depending on which dependency manager you use. For Cocoapods see  ",(0,i.kt)("a",{parentName:"p",href:"/KMMBridge/docs/docs/IOS_LOCAL_DEV_COCOAPODS"},"IOS_LOCAL_DEV_COCOAPODS"),". For SPM see  ",(0,i.kt)("a",{parentName:"p",href:"/KMMBridge/docs/docs/IOS_LOCAL_DEV_SPM"},"IOS_LOCAL_DEV_SPM"),"."),(0,i.kt)("h2",{id:"private-github-releases"},"Private Github Releases"),(0,i.kt)("p",null,"If you are using private Github artifacts, you'll need to add auth info for that to work. See ",(0,i.kt)("a",{parentName:"p",href:"/KMMBridge/docs/docs/GITHUB_RELEASE_ARTIFACTS#private-repos"},"GITHUB_RELEASE_ARTIFACTS"),"."),(0,i.kt)("p",null,(0,i.kt)("em",{parentName:"p"},"You must do this before attempting to integrate dependency managers!!!")),(0,i.kt)("h2",{id:"using-cocoapods"},"Using Cocoapods"),(0,i.kt)("p",null,"See:  ",(0,i.kt)("a",{parentName:"p",href:"/KMMBridge/docs/docs/IOS_COCOAPODS"},"IOS_COCOAPODS")),(0,i.kt)("h2",{id:"using-spm"},"Using SPM"),(0,i.kt)("p",null,"See: ",(0,i.kt)("a",{parentName:"p",href:"/KMMBridge/docs/docs/IOS_SPM"},"IOS_SPM")))}f.isMDXComponent=!0}}]);