"use strict";(self.webpackChunktouchlab=self.webpackChunktouchlab||[]).push([[3026],{3905:(e,r,t)=>{t.d(r,{Zo:()=>c,kt:()=>h});var o=t(7294);function a(e,r,t){return r in e?Object.defineProperty(e,r,{value:t,enumerable:!0,configurable:!0,writable:!0}):e[r]=t,e}function n(e,r){var t=Object.keys(e);if(Object.getOwnPropertySymbols){var o=Object.getOwnPropertySymbols(e);r&&(o=o.filter((function(r){return Object.getOwnPropertyDescriptor(e,r).enumerable}))),t.push.apply(t,o)}return t}function i(e){for(var r=1;r<arguments.length;r++){var t=null!=arguments[r]?arguments[r]:{};r%2?n(Object(t),!0).forEach((function(r){a(e,r,t[r])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(t)):n(Object(t)).forEach((function(r){Object.defineProperty(e,r,Object.getOwnPropertyDescriptor(t,r))}))}return e}function s(e,r){if(null==e)return{};var t,o,a=function(e,r){if(null==e)return{};var t,o,a={},n=Object.keys(e);for(o=0;o<n.length;o++)t=n[o],r.indexOf(t)>=0||(a[t]=e[t]);return a}(e,r);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(e);for(o=0;o<n.length;o++)t=n[o],r.indexOf(t)>=0||Object.prototype.propertyIsEnumerable.call(e,t)&&(a[t]=e[t])}return a}var l=o.createContext({}),d=function(e){var r=o.useContext(l),t=r;return e&&(t="function"==typeof e?e(r):i(i({},r),e)),t},c=function(e){var r=d(e.components);return o.createElement(l.Provider,{value:r},e.children)},u="mdxType",m={inlineCode:"code",wrapper:function(e){var r=e.children;return o.createElement(o.Fragment,{},r)}},p=o.forwardRef((function(e,r){var t=e.components,a=e.mdxType,n=e.originalType,l=e.parentName,c=s(e,["components","mdxType","originalType","parentName"]),u=d(t),p=a,h=u["".concat(l,".").concat(p)]||u[p]||m[p]||n;return t?o.createElement(h,i(i({ref:r},c),{},{components:t})):o.createElement(h,i({ref:r},c))}));function h(e,r){var t=arguments,a=r&&r.mdxType;if("string"==typeof e||a){var n=t.length,i=new Array(n);i[0]=p;var s={};for(var l in r)hasOwnProperty.call(r,l)&&(s[l]=r[l]);s.originalType=e,s[u]="string"==typeof e?e:a,i[1]=s;for(var d=2;d<n;d++)i[d]=t[d];return o.createElement.apply(null,i)}return o.createElement.apply(null,t)}p.displayName="MDXCreateElement"},2491:(e,r,t)=>{t.r(r),t.d(r,{assets:()=>l,contentTitle:()=>i,default:()=>u,frontMatter:()=>n,metadata:()=>s,toc:()=>d});var o=t(7462),a=(t(7294),t(3905));const n={sidebar_position:8},i="Troubleshooting",s={unversionedId:"TROUBLESHOOTING",id:"version-0.3.x/TROUBLESHOOTING",title:"Troubleshooting",description:'Error: "This fat framework already has a binary for architecture x64 (common for target ios_x64) (or similar for arm)"',source:"@site/versioned_docs/version-0.3.x/TROUBLESHOOTING.md",sourceDirName:".",slug:"/TROUBLESHOOTING",permalink:"/docs/0.3.x/TROUBLESHOOTING",draft:!1,editUrl:"https://github.com/touchlab/KMMBridge/tree/main/website/versioned_docs/version-0.3.x/TROUBLESHOOTING.md",tags:[],version:"0.3.x",lastUpdatedBy:"Gustavo F\xe3o Valvassori",lastUpdatedAt:1723058363,formattedLastUpdatedAt:"Aug 7, 2024",sidebarPosition:8,frontMatter:{sidebar_position:8},sidebar:"tutorialSidebar",previous:{title:"iOS Dev Setup",permalink:"/docs/0.3.x/IOS_DEV_SETUP"},next:{title:"General Documentation",permalink:"/docs/0.3.x/category/general-documentation"}},l={},d=[{value:"Error: &quot;This fat framework already has a binary for architecture <code>x64</code> (common for target <code>ios_x64</code>) (or similar for arm)&quot;",id:"error-this-fat-framework-already-has-a-binary-for-architecture-x64-common-for-target-ios_x64-or-similar-for-arm",level:3},{value:"Error: &quot;Received status code 422 from server: Unprocessable Entity&quot; when using GitHub Packages&quot;",id:"error-received-status-code-422-from-server-unprocessable-entity-when-using-github-packages",level:3},{value:"Error: &quot;Cannot add task &#39;assembleSdk_sharedReleaseXCFramework&#39; as a task with that name already exists.&quot;",id:"error-cannot-add-task-assemblesdk_sharedreleasexcframework-as-a-task-with-that-name-already-exists",level:3},{value:"Error: &quot;Could not PUT &#39;https://maven.pkg.github.com/MyOrg/MyRepo/MyRepo/shared-kmmbridge/0.0.1/shared-kmmbridge-0.0.1.zip&#39;. Received status code 403 from server: Forbidden&quot;",id:"error-could-not-put-httpsmavenpkggithubcommyorgmyrepomyreposhared-kmmbridge001shared-kmmbridge-001zip-received-status-code-403-from-server-forbidden",level:3},{value:"Error: Task &#39;kmmBridgePublish&#39; not found in root project &#39;MyProject&#39;.",id:"error-task-kmmbridgepublish-not-found-in-root-project-myproject",level:3}],c={toc:d};function u(e){let{components:r,...t}=e;return(0,a.kt)("wrapper",(0,o.Z)({},c,t,{components:r,mdxType:"MDXLayout"}),(0,a.kt)("h1",{id:"troubleshooting"},"Troubleshooting"),(0,a.kt)("h3",{id:"error-this-fat-framework-already-has-a-binary-for-architecture-x64-common-for-target-ios_x64-or-similar-for-arm"},'Error: "This fat framework already has a binary for architecture ',(0,a.kt)("inlineCode",{parentName:"h3"},"x64")," (common for target ",(0,a.kt)("inlineCode",{parentName:"h3"},"ios_x64"),') (or similar for arm)"'),(0,a.kt)("p",null,"This is basically saying you have more than one framework defined for the same architecture. This most commonly happens\nbecause the project has both explicit frameworks defined in the kotlin/targets area, and the CocoaPods plugin applied."),(0,a.kt)("p",null,"If you see ",(0,a.kt)("inlineCode",{parentName:"p"},'kotlin("native.cocoapods")')," or ",(0,a.kt)("inlineCode",{parentName:"p"},'id("org.jetbrains.kotlin.native.cocoapods")')," in the plugins:"),(0,a.kt)("pre",null,(0,a.kt)("code",{parentName:"pre",className:"language-kotlin"},'plugins {\n    kotlin("multiplatform")\n    kotlin("native.cocoapods") // <--- This\n    id("co.touchlab.faktory.kmmbridge") version "{{VERSION_NAME_3X}}"\n}\n')),(0,a.kt)("p",null,"and you see framework declarations for your targets:"),(0,a.kt)("pre",null,(0,a.kt)("code",{parentName:"pre",className:"language-kotlin"},"kotlin {\n    iosX64 {\n        binaries.framework()\n    }\n}\n")),(0,a.kt)("p",null,"You have duplicate frameworks being declared. The CocoaPods plugin is adding frameworks automatically for all darwin/Apple\ntargets, so explicitly declaring them is redundant."),(0,a.kt)("h3",{id:"error-received-status-code-422-from-server-unprocessable-entity-when-using-github-packages"},'Error: "Received status code 422 from server: Unprocessable Entity" when using GitHub Packages"'),(0,a.kt)("p",null,"If you have multiple repos publishing to the same group and artifact, you'll get this error. Changing the\nartifact name fixes it. See below."),(0,a.kt)("p",null,(0,a.kt)("a",{parentName:"p",href:"https://github.com/orgs/community/discussions/23474"},"https://github.com/orgs/community/discussions/23474")),(0,a.kt)("h3",{id:"error-cannot-add-task-assemblesdk_sharedreleasexcframework-as-a-task-with-that-name-already-exists"},"Error: \"Cannot add task 'assembleSdk_sharedReleaseXCFramework' as a task with that name already exists.\""),(0,a.kt)("p",null,"In your Gradle script, if you're creating your own ",(0,a.kt)("inlineCode",{parentName:"p"},"XCFramework")," like ",(0,a.kt)("inlineCode",{parentName:"p"},'XCFramework("<name>") then you should remove it as '),"KMMBridge",(0,a.kt)("inlineCode",{parentName:"p"},"creates the"),"XCFramework` definitions automatically."),(0,a.kt)("p",null,"We might add ability in future to automatically detect this and handle smoothly."),(0,a.kt)("h3",{id:"error-could-not-put-httpsmavenpkggithubcommyorgmyrepomyreposhared-kmmbridge001shared-kmmbridge-001zip-received-status-code-403-from-server-forbidden"},"Error: \"Could not PUT '",(0,a.kt)("a",{parentName:"h3",href:"https://maven.pkg.github.com/MyOrg/MyRepo/MyRepo/shared-kmmbridge/0.0.1/shared-kmmbridge-0.0.1.zip'"},"https://maven.pkg.github.com/MyOrg/MyRepo/MyRepo/shared-kmmbridge/0.0.1/shared-kmmbridge-0.0.1.zip'"),'. Received status code 403 from server: Forbidden"'),(0,a.kt)("p",null,"You're publishing to github packages, but the workflow doesn't have the right permissions. You can add a permissions block like the following to fix this:"),(0,a.kt)("pre",null,(0,a.kt)("code",{parentName:"pre",className:"language-yaml"},"permissions:\n  contents: write\n  packages: write\n")),(0,a.kt)("p",null,"That block can be at the top-level or inside the publish task. ",(0,a.kt)("a",{parentName:"p",href:"https://github.com/touchlab/KMMBridgeSampleKotlin/blob/main/.github/workflows/main.yml"},"See here")," for an example."),(0,a.kt)("h3",{id:"error-task-kmmbridgepublish-not-found-in-root-project-myproject"},"Error: Task 'kmmBridgePublish' not found in root project 'MyProject'."),(0,a.kt)("p",null,"KMMBridge won't configure its publication tasks unless it knows you want it to. You must set the ",(0,a.kt)("inlineCode",{parentName:"p"},"ENABLE_PUBLISHING")," gradle property to true (usually only in CI or when troubleshooting), and you must have publication fully configured, including setting an ",(0,a.kt)("inlineCode",{parentName:"p"},"artifactManager")," and ",(0,a.kt)("inlineCode",{parentName:"p"},"dependencyManager"),"."))}u.isMDXComponent=!0}}]);