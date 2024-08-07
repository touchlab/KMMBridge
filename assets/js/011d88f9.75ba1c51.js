"use strict";(self.webpackChunktouchlab=self.webpackChunktouchlab||[]).push([[9757],{3905:(e,t,o)=>{o.d(t,{Zo:()=>p,kt:()=>f});var n=o(7294);function a(e,t,o){return t in e?Object.defineProperty(e,t,{value:o,enumerable:!0,configurable:!0,writable:!0}):e[t]=o,e}function r(e,t){var o=Object.keys(e);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(e);t&&(n=n.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),o.push.apply(o,n)}return o}function i(e){for(var t=1;t<arguments.length;t++){var o=null!=arguments[t]?arguments[t]:{};t%2?r(Object(o),!0).forEach((function(t){a(e,t,o[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(o)):r(Object(o)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(o,t))}))}return e}function l(e,t){if(null==e)return{};var o,n,a=function(e,t){if(null==e)return{};var o,n,a={},r=Object.keys(e);for(n=0;n<r.length;n++)o=r[n],t.indexOf(o)>=0||(a[o]=e[o]);return a}(e,t);if(Object.getOwnPropertySymbols){var r=Object.getOwnPropertySymbols(e);for(n=0;n<r.length;n++)o=r[n],t.indexOf(o)>=0||Object.prototype.propertyIsEnumerable.call(e,o)&&(a[o]=e[o])}return a}var s=n.createContext({}),c=function(e){var t=n.useContext(s),o=t;return e&&(o="function"==typeof e?e(t):i(i({},t),e)),o},p=function(e){var t=c(e.components);return n.createElement(s.Provider,{value:t},e.children)},u="mdxType",d={inlineCode:"code",wrapper:function(e){var t=e.children;return n.createElement(n.Fragment,{},t)}},m=n.forwardRef((function(e,t){var o=e.components,a=e.mdxType,r=e.originalType,s=e.parentName,p=l(e,["components","mdxType","originalType","parentName"]),u=c(o),m=a,f=u["".concat(s,".").concat(m)]||u[m]||d[m]||r;return o?n.createElement(f,i(i({ref:t},p),{},{components:o})):n.createElement(f,i({ref:t},p))}));function f(e,t){var o=arguments,a=t&&t.mdxType;if("string"==typeof e||a){var r=o.length,i=new Array(r);i[0]=m;var l={};for(var s in t)hasOwnProperty.call(t,s)&&(l[s]=t[s]);l.originalType=e,l[u]="string"==typeof e?e:a,i[1]=l;for(var c=2;c<r;c++)i[c]=o[c];return n.createElement.apply(null,i)}return n.createElement.apply(null,o)}m.displayName="MDXCreateElement"},6765:(e,t,o)=>{o.r(t),o.d(t,{assets:()=>s,contentTitle:()=>i,default:()=>u,frontMatter:()=>r,metadata:()=>l,toc:()=>c});var n=o(7462),a=(o(7294),o(3905));const r={},i="SPM Local Dev Flow",l={unversionedId:"spm/IOS_LOCAL_DEV_SPM",id:"spm/IOS_LOCAL_DEV_SPM",title:"SPM Local Dev Flow",description:"After you have integrated your Kotlin module into Xcode using SPM, you may want to locally build and test your Kotlin code when making changes.",source:"@site/docs/spm/02_IOS_LOCAL_DEV_SPM.md",sourceDirName:"spm",slug:"/spm/IOS_LOCAL_DEV_SPM",permalink:"/docs/spm/IOS_LOCAL_DEV_SPM",draft:!1,editUrl:"https://github.com/touchlab/KMMBridge/tree/main/website/docs/spm/02_IOS_LOCAL_DEV_SPM.md",tags:[],version:"current",lastUpdatedBy:"Gustavo F\xe3o Valvassori",lastUpdatedAt:1723058363,formattedLastUpdatedAt:"Aug 7, 2024",sidebarPosition:2,frontMatter:{},sidebar:"tutorialSidebar",previous:{title:"Using Swift Package Manager",permalink:"/docs/spm/IOS_SPM"},next:{title:"Porting from KMMBridge 0.3.x",permalink:"/docs/PORTING_0.3.x"}},s={},c=[],p={toc:c};function u(e){let{components:t,...o}=e;return(0,a.kt)("wrapper",(0,n.Z)({},p,o,{components:t,mdxType:"MDXLayout"}),(0,a.kt)("h1",{id:"spm-local-dev-flow"},"SPM Local Dev Flow"),(0,a.kt)("p",null,"After you have ",(0,a.kt)("a",{parentName:"p",href:"/docs/spm/IOS_SPM"},"integrated your Kotlin module into Xcode using SPM"),", you may want to locally build and test your Kotlin code when making changes."),(0,a.kt)("blockquote",null,(0,a.kt)("p",{parentName:"blockquote"},"As mentioned, SPM is not really integrated out of the box with Kotlin, and SPM itself does not make integrations easy (generally speaking). We have some support for local dev flow, but we are looking for feedback and improvements.")),(0,a.kt)("p",null,"Since KMMBridge is generating your ",(0,a.kt)("inlineCode",{parentName:"p"},"Package.swift")," files, the first step is to run a dev build step to build the local dev ",(0,a.kt)("inlineCode",{parentName:"p"},"Package.swift")," and locally build a debug version of the Kotlin code."),(0,a.kt)("pre",null,(0,a.kt)("code",{parentName:"pre",className:"language-shell"},"./gradlew spmDevBuild\n")),(0,a.kt)("p",null,"This should:"),(0,a.kt)("ul",null,(0,a.kt)("li",{parentName:"ul"},"Build a debug version of the XCFramework"),(0,a.kt)("li",{parentName:"ul"},"Write a local dev path to ",(0,a.kt)("inlineCode",{parentName:"li"},"Package.swift"))),(0,a.kt)("p",null,"Next, you need to manually copy the whole Kotlin project into Xcode. That means, quite literally, drag the Kotlin project's folder into Xcode."),(0,a.kt)("videoEmbed",{videoUrl:"https://tl-navigator-images.s3.amazonaws.com/docimages/dragspm.mp4"}),(0,a.kt)("p",null,"In the sample above, the pacakge ",(0,a.kt)("inlineCode",{parentName:"p"},"allshared")," is inside ",(0,a.kt)("inlineCode",{parentName:"p"},"KevsKmmTest"),". When you drag it in, if Xcode properly recognizes it, you'll see ",(0,a.kt)("inlineCode",{parentName:"p"},"allshared")," disappear, but when you build, things should work as expected."),(0,a.kt)("admonition",{type:"caution"},(0,a.kt)("p",{parentName:"admonition"},"Xcode is picky about the setup here. If the folder name of the local folder you drag in differs from the name of the git repo that you have for the library, Xcode won't use it. It attempts to make sure you are using the right project, but it can fail to validate, which is pretty confusing. If you get errors trying to use a local build, be aware that Xcode can fail for non-obvious reasons.")),(0,a.kt)("p",null,"When you run ",(0,a.kt)("inlineCode",{parentName:"p"},"spmDevBuild"),", it will build all architectures, which you probably don't need for testing on a simulator. To restrict architectures when building, you can pass in a Gradle param."),(0,a.kt)("pre",null,(0,a.kt)("code",{parentName:"pre",className:"language-shell"},"./gradlew spmDevBuild -PspmBuildTargets=ios_simulator_arm64\n")),(0,a.kt)("admonition",{type:"note"},(0,a.kt)("p",{parentName:"admonition"},(0,a.kt)("inlineCode",{parentName:"p"},"ios_simulator_arm64")," is for mac Arm machines (M1, M2, etc). For Intel Macs, use ",(0,a.kt)("inlineCode",{parentName:"p"},"ios_x64"),".")),(0,a.kt)("p",null,'When you are done making and testing local changes, select the folder you dragged in, and remove it by right-clicking it and selecting "Delete". Make sure to select "Remove References" on the popup. Xcode should then reload the remote version you had previously.'),(0,a.kt)("videoEmbed",{videoUrl:"https://tl-navigator-images.s3.amazonaws.com/docimages/removelocal.mp4"}))}u.isMDXComponent=!0}}]);