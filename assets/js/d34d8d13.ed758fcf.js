"use strict";(self.webpackChunktouchlab=self.webpackChunktouchlab||[]).push([[6308],{3905:(e,t,n)=>{n.d(t,{Zo:()=>c,kt:()=>g});var r=n(7294);function a(e,t,n){return t in e?Object.defineProperty(e,t,{value:n,enumerable:!0,configurable:!0,writable:!0}):e[t]=n,e}function o(e,t){var n=Object.keys(e);if(Object.getOwnPropertySymbols){var r=Object.getOwnPropertySymbols(e);t&&(r=r.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),n.push.apply(n,r)}return n}function l(e){for(var t=1;t<arguments.length;t++){var n=null!=arguments[t]?arguments[t]:{};t%2?o(Object(n),!0).forEach((function(t){a(e,t,n[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(n)):o(Object(n)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(n,t))}))}return e}function i(e,t){if(null==e)return{};var n,r,a=function(e,t){if(null==e)return{};var n,r,a={},o=Object.keys(e);for(r=0;r<o.length;r++)n=o[r],t.indexOf(n)>=0||(a[n]=e[n]);return a}(e,t);if(Object.getOwnPropertySymbols){var o=Object.getOwnPropertySymbols(e);for(r=0;r<o.length;r++)n=o[r],t.indexOf(n)>=0||Object.prototype.propertyIsEnumerable.call(e,n)&&(a[n]=e[n])}return a}var s=r.createContext({}),p=function(e){var t=r.useContext(s),n=t;return e&&(n="function"==typeof e?e(t):l(l({},t),e)),n},c=function(e){var t=p(e.components);return r.createElement(s.Provider,{value:t},e.children)},u="mdxType",d={inlineCode:"code",wrapper:function(e){var t=e.children;return r.createElement(r.Fragment,{},t)}},m=r.forwardRef((function(e,t){var n=e.components,a=e.mdxType,o=e.originalType,s=e.parentName,c=i(e,["components","mdxType","originalType","parentName"]),u=p(n),m=a,g=u["".concat(s,".").concat(m)]||u[m]||d[m]||o;return n?r.createElement(g,l(l({ref:t},c),{},{components:n})):r.createElement(g,l({ref:t},c))}));function g(e,t){var n=arguments,a=t&&t.mdxType;if("string"==typeof e||a){var o=n.length,l=new Array(o);l[0]=m;var i={};for(var s in t)hasOwnProperty.call(t,s)&&(i[s]=t[s]);i.originalType=e,i[u]="string"==typeof e?e:a,l[1]=i;for(var p=2;p<o;p++)l[p]=n[p];return r.createElement.apply(null,l)}return r.createElement.apply(null,n)}m.displayName="MDXCreateElement"},559:(e,t,n)=>{n.r(t),n.d(t,{assets:()=>s,contentTitle:()=>l,default:()=>u,frontMatter:()=>o,metadata:()=>i,toc:()=>p});var r=n(7462),a=(n(7294),n(3905));const o={},l="SPM Local Dev Flow",i={unversionedId:"spm/IOS_LOCAL_DEV_SPM",id:"version-0.3.x/spm/IOS_LOCAL_DEV_SPM",title:"SPM Local Dev Flow",description:"After you have integrated your Kotlin module into Xcode using SPM, you may want to locally build and test your Kotlin code when making changes.",source:"@site/versioned_docs/version-0.3.x/spm/02_IOS_LOCAL_DEV_SPM.md",sourceDirName:"spm",slug:"/spm/IOS_LOCAL_DEV_SPM",permalink:"/docs/0.3.x/spm/IOS_LOCAL_DEV_SPM",draft:!1,editUrl:"https://github.com/touchlab/KMMBridge/tree/main/website/versioned_docs/version-0.3.x/spm/02_IOS_LOCAL_DEV_SPM.md",tags:[],version:"0.3.x",lastUpdatedBy:"Gustavo F\xe3o Valvassori",lastUpdatedAt:1723058363,formattedLastUpdatedAt:"Aug 7, 2024",sidebarPosition:2,frontMatter:{},sidebar:"tutorialSidebar",previous:{title:"Using Swift Package Manager",permalink:"/docs/0.3.x/spm/IOS_SPM"}},s={},p=[],c={toc:p};function u(e){let{components:t,...n}=e;return(0,a.kt)("wrapper",(0,r.Z)({},c,n,{components:t,mdxType:"MDXLayout"}),(0,a.kt)("h1",{id:"spm-local-dev-flow"},"SPM Local Dev Flow"),(0,a.kt)("p",null,"After you have ",(0,a.kt)("a",{parentName:"p",href:"/docs/0.3.x/spm/IOS_SPM"},"integrated your Kotlin module into Xcode using SPM"),", you may want to locally build and test your Kotlin code when making changes."),(0,a.kt)("p",null,(0,a.kt)("strong",{parentName:"p"},(0,a.kt)("em",{parentName:"strong"},"Experimental:"))," ",(0,a.kt)("em",{parentName:"p"},"While all of KMMBridge is relatively new, the SPM dev flow is very experimental. The most likely issues with be path resolutions.")),(0,a.kt)("blockquote",null,(0,a.kt)("p",{parentName:"blockquote"},"As mentioned, SPM is not really integrated out of the box with Kotlin, and SPM itself does not make integrations easy (generally speaking). We have some support for local dev flow, but we are looking for feedback and improvements.")),(0,a.kt)("p",null,"Since KMMBridge is generating your ",(0,a.kt)("inlineCode",{parentName:"p"},"Package.swift")," files, the first step is to run a dev build step to build the local dev ",(0,a.kt)("inlineCode",{parentName:"p"},"Package.swift")," and locally build a debug version of the Kotlin code."),(0,a.kt)("pre",null,(0,a.kt)("code",{parentName:"pre",className:"language-shell"},"./gradlew spmDevBuild\n")),(0,a.kt)("p",null,"This should:"),(0,a.kt)("ul",null,(0,a.kt)("li",{parentName:"ul"},"Build a debug version of the XCFramework"),(0,a.kt)("li",{parentName:"ul"},"Write a local dev path to ",(0,a.kt)("inlineCode",{parentName:"li"},"Package.swift"))),(0,a.kt)("p",null,"Next, you need to manually copy the whole Kotlin project into Xcode. That means, quite literally, drag the Kotlin project's folder into Xcode."),(0,a.kt)("video",{src:"https://tl-navigator-images.s3.amazonaws.com/docimages/dragspm.mp4"}),(0,a.kt)("p",null,"In the sample above, the pacakge ",(0,a.kt)("inlineCode",{parentName:"p"},"allshared")," is inside ",(0,a.kt)("inlineCode",{parentName:"p"},"KevsKmmTest"),". When you drag it in, if Xcode properly recognizes it, you'll see ",(0,a.kt)("inlineCode",{parentName:"p"},"allshared")," disappear, but when you build, things should work as expected."),(0,a.kt)("p",null,"When you run ",(0,a.kt)("inlineCode",{parentName:"p"},"spmDevBuild"),", it will build all architectures, which you probably don't need for testing on a simulator. To restrict architectures when building, you can pass in a Gradle param."),(0,a.kt)("pre",null,(0,a.kt)("code",{parentName:"pre",className:"language-shell"},"./gradlew spmDevBuild -PspmBuildTargets=ios_x64\n")),(0,a.kt)("p",null,"For Intel Macs, use ",(0,a.kt)("inlineCode",{parentName:"p"},"ios_x64"),". For arm Macs, use ",(0,a.kt)("inlineCode",{parentName:"p"},"ios_simulator_arm64"),". You can pass in multiple architectures by separating them with commas."),(0,a.kt)("p",null,'When you are done making and testing local changes, select the folder you dragged in, and remove it by right-clicking it and selecting "Delete". Make sure to select "Remove References" on the popup. Xcode should then reload the remote version you had previously.'),(0,a.kt)("video",{src:"https://tl-navigator-images.s3.amazonaws.com/docimages/removelocal.mp4"}))}u.isMDXComponent=!0}}]);