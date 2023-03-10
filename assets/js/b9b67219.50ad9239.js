"use strict";(self.webpackChunktouchlab=self.webpackChunktouchlab||[]).push([[3],{3905:(e,r,t)=>{t.d(r,{Zo:()=>u,kt:()=>f});var a=t(7294);function o(e,r,t){return r in e?Object.defineProperty(e,r,{value:t,enumerable:!0,configurable:!0,writable:!0}):e[r]=t,e}function n(e,r){var t=Object.keys(e);if(Object.getOwnPropertySymbols){var a=Object.getOwnPropertySymbols(e);r&&(a=a.filter((function(r){return Object.getOwnPropertyDescriptor(e,r).enumerable}))),t.push.apply(t,a)}return t}function i(e){for(var r=1;r<arguments.length;r++){var t=null!=arguments[r]?arguments[r]:{};r%2?n(Object(t),!0).forEach((function(r){o(e,r,t[r])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(t)):n(Object(t)).forEach((function(r){Object.defineProperty(e,r,Object.getOwnPropertyDescriptor(t,r))}))}return e}function s(e,r){if(null==e)return{};var t,a,o=function(e,r){if(null==e)return{};var t,a,o={},n=Object.keys(e);for(a=0;a<n.length;a++)t=n[a],r.indexOf(t)>=0||(o[t]=e[t]);return o}(e,r);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(e);for(a=0;a<n.length;a++)t=n[a],r.indexOf(t)>=0||Object.prototype.propertyIsEnumerable.call(e,t)&&(o[t]=e[t])}return o}var l=a.createContext({}),c=function(e){var r=a.useContext(l),t=r;return e&&(t="function"==typeof e?e(r):i(i({},r),e)),t},u=function(e){var r=c(e.components);return a.createElement(l.Provider,{value:r},e.children)},d="mdxType",m={inlineCode:"code",wrapper:function(e){var r=e.children;return a.createElement(a.Fragment,{},r)}},p=a.forwardRef((function(e,r){var t=e.components,o=e.mdxType,n=e.originalType,l=e.parentName,u=s(e,["components","mdxType","originalType","parentName"]),d=c(t),p=o,f=d["".concat(l,".").concat(p)]||d[p]||m[p]||n;return t?a.createElement(f,i(i({ref:r},u),{},{components:t})):a.createElement(f,i({ref:r},u))}));function f(e,r){var t=arguments,o=r&&r.mdxType;if("string"==typeof e||o){var n=t.length,i=new Array(n);i[0]=p;var s={};for(var l in r)hasOwnProperty.call(r,l)&&(s[l]=r[l]);s.originalType=e,s[d]="string"==typeof e?e:o,i[1]=s;for(var c=2;c<n;c++)i[c]=t[c];return a.createElement.apply(null,i)}return a.createElement.apply(null,t)}p.displayName="MDXCreateElement"},2128:(e,r,t)=>{t.r(r),t.d(r,{assets:()=>l,contentTitle:()=>i,default:()=>d,frontMatter:()=>n,metadata:()=>s,toc:()=>c});var a=t(7462),o=(t(7294),t(3905));const n={sidebar_position:8},i="Troubleshooting",s={unversionedId:"TROUBLESHOOTING",id:"TROUBLESHOOTING",title:"Troubleshooting",description:'Error: "This fat framework already has a binary for architecture x64 (common for target ios_x64) (or similar for arm)"',source:"@site/docs/TROUBLESHOOTING.md",sourceDirName:".",slug:"/TROUBLESHOOTING",permalink:"/docs/TROUBLESHOOTING",draft:!1,editUrl:"https://github.com/touchlab/KMMBridge/tree/main/website/docs/TROUBLESHOOTING.md",tags:[],version:"current",lastUpdatedBy:"Russell Wolf",lastUpdatedAt:1678461721,formattedLastUpdatedAt:"Mar 10, 2023",sidebarPosition:8,frontMatter:{sidebar_position:8},sidebar:"tutorialSidebar",previous:{title:"iOS Dev Setup",permalink:"/docs/IOS_DEV_SETUP"},next:{title:"General Documentation",permalink:"/docs/category/general-documentation"}},l={},c=[{value:"Error: &quot;This fat framework already has a binary for architecture <code>x64</code> (common for target <code>ios_x64</code>) (or similar for arm)&quot;",id:"error-this-fat-framework-already-has-a-binary-for-architecture-x64-common-for-target-ios_x64-or-similar-for-arm",level:3},{value:"Error: &quot;Received status code 422 from server: Unprocessable Entity&quot; when using GitHub Packages&quot;",id:"error-received-status-code-422-from-server-unprocessable-entity-when-using-github-packages",level:3},{value:"Error: &quot;Cannot add task &#39;assembleSdk_sharedReleaseXCFramework&#39; as a task with that name already exists.&quot;",id:"error-cannot-add-task-assemblesdk_sharedreleasexcframework-as-a-task-with-that-name-already-exists",level:3}],u={toc:c};function d(e){let{components:r,...t}=e;return(0,o.kt)("wrapper",(0,a.Z)({},u,t,{components:r,mdxType:"MDXLayout"}),(0,o.kt)("h1",{id:"troubleshooting"},"Troubleshooting"),(0,o.kt)("h3",{id:"error-this-fat-framework-already-has-a-binary-for-architecture-x64-common-for-target-ios_x64-or-similar-for-arm"},'Error: "This fat framework already has a binary for architecture ',(0,o.kt)("inlineCode",{parentName:"h3"},"x64")," (common for target ",(0,o.kt)("inlineCode",{parentName:"h3"},"ios_x64"),') (or similar for arm)"'),(0,o.kt)("p",null,"This is basically saying you have more than one framework defined for the same architecture. This most commonly happens\nbecause the project has both explicit frameworks defined in the kotlin/targets area, and the CocoaPods plugin applied."),(0,o.kt)("p",null,"If you see ",(0,o.kt)("inlineCode",{parentName:"p"},'kotlin("native.cocoapods")')," or ",(0,o.kt)("inlineCode",{parentName:"p"},'id("org.jetbrains.kotlin.native.cocoapods")')," in the plugins:"),(0,o.kt)("pre",null,(0,o.kt)("code",{parentName:"pre",className:"language-kotlin"},'plugins {\n    kotlin("multiplatform")\n    kotlin("native.cocoapods") // <--- This\n    id("co.touchlab.faktory.kmmbridge") version "0.3.7"\n}\n')),(0,o.kt)("p",null,"and you see framework declarations for your targets:"),(0,o.kt)("pre",null,(0,o.kt)("code",{parentName:"pre",className:"language-kotlin"},"kotlin {\n    iosX64 {\n        binaries.framework()\n    }\n}\n")),(0,o.kt)("p",null,"You have duplicate frameworks being declared. The CocoaPods plugin is adding frameworks automatically for all darwin/Apple\ntargets, so explicitly declaring them is redundant."),(0,o.kt)("h3",{id:"error-received-status-code-422-from-server-unprocessable-entity-when-using-github-packages"},'Error: "Received status code 422 from server: Unprocessable Entity" when using GitHub Packages"'),(0,o.kt)("p",null,"If you have multiple repos publishing to the same group and artifact, you'll get this error. Changing the\nartifact name fixes it. See below."),(0,o.kt)("p",null,(0,o.kt)("a",{parentName:"p",href:"https://github.com/orgs/community/discussions/23474"},"https://github.com/orgs/community/discussions/23474")),(0,o.kt)("h3",{id:"error-cannot-add-task-assemblesdk_sharedreleasexcframework-as-a-task-with-that-name-already-exists"},"Error: \"Cannot add task 'assembleSdk_sharedReleaseXCFramework' as a task with that name already exists.\""),(0,o.kt)("p",null,"In your Gradle script, if you're creating your own ",(0,o.kt)("inlineCode",{parentName:"p"},"XCFramework")," like ",(0,o.kt)("inlineCode",{parentName:"p"},'XCFramework("<name>") then you should remove it as '),"KMMBridge",(0,o.kt)("inlineCode",{parentName:"p"},"creates the"),"XCFramework` definitions automatically."),(0,o.kt)("p",null,"We might add ability in future to automatically detect this and handle smoothly."))}d.isMDXComponent=!0}}]);