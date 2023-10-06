"use strict";(self.webpackChunktouchlab=self.webpackChunktouchlab||[]).push([[4510],{3905:(e,n,r)=>{r.d(n,{Zo:()=>d,kt:()=>m});var t=r(7294);function i(e,n,r){return n in e?Object.defineProperty(e,n,{value:r,enumerable:!0,configurable:!0,writable:!0}):e[n]=r,e}function o(e,n){var r=Object.keys(e);if(Object.getOwnPropertySymbols){var t=Object.getOwnPropertySymbols(e);n&&(t=t.filter((function(n){return Object.getOwnPropertyDescriptor(e,n).enumerable}))),r.push.apply(r,t)}return r}function a(e){for(var n=1;n<arguments.length;n++){var r=null!=arguments[n]?arguments[n]:{};n%2?o(Object(r),!0).forEach((function(n){i(e,n,r[n])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(r)):o(Object(r)).forEach((function(n){Object.defineProperty(e,n,Object.getOwnPropertyDescriptor(r,n))}))}return e}function s(e,n){if(null==e)return{};var r,t,i=function(e,n){if(null==e)return{};var r,t,i={},o=Object.keys(e);for(t=0;t<o.length;t++)r=o[t],n.indexOf(r)>=0||(i[r]=e[r]);return i}(e,n);if(Object.getOwnPropertySymbols){var o=Object.getOwnPropertySymbols(e);for(t=0;t<o.length;t++)r=o[t],n.indexOf(r)>=0||Object.prototype.propertyIsEnumerable.call(e,r)&&(i[r]=e[r])}return i}var l=t.createContext({}),u=function(e){var n=t.useContext(l),r=n;return e&&(r="function"==typeof e?e(n):a(a({},n),e)),r},d=function(e){var n=u(e.components);return t.createElement(l.Provider,{value:n},e.children)},c="mdxType",p={inlineCode:"code",wrapper:function(e){var n=e.children;return t.createElement(t.Fragment,{},n)}},g=t.forwardRef((function(e,n){var r=e.components,i=e.mdxType,o=e.originalType,l=e.parentName,d=s(e,["components","mdxType","originalType","parentName"]),c=u(r),g=i,m=c["".concat(l,".").concat(g)]||c[g]||p[g]||o;return r?t.createElement(m,a(a({ref:n},d),{},{components:r})):t.createElement(m,a({ref:n},d))}));function m(e,n){var r=arguments,i=n&&n.mdxType;if("string"==typeof e||i){var o=r.length,a=new Array(o);a[0]=g;var s={};for(var l in n)hasOwnProperty.call(n,l)&&(s[l]=n[l]);s.originalType=e,s[c]="string"==typeof e?e:i,a[1]=s;for(var u=2;u<o;u++)a[u]=r[u];return t.createElement.apply(null,a)}return t.createElement.apply(null,r)}g.displayName="MDXCreateElement"},5334:(e,n,r)=>{r.r(n),r.d(n,{assets:()=>l,contentTitle:()=>a,default:()=>c,frontMatter:()=>o,metadata:()=>s,toc:()=>u});var t=r(7462),i=(r(7294),r(3905));const o={title:"Android Versioning",sidebar_position:2},a="Publishing Android Alongside KMMBridge",s={unversionedId:"general/ANDROID_VERSIONING",id:"version-0.3.x/general/ANDROID_VERSIONING",title:"Android Versioning",description:"KMMBridge is primarily a tool for managing KMM publications for iOS. It does not implicitly do any Android publication, but it's reasonable for a team to desire to publish both iOS and Android simultaneously. Accomplishing that simply comes down to updating your CI workflows to also run the appropriate android publish tasks.",source:"@site/versioned_docs/version-0.3.x/general/ANDROID_VERSIONING.md",sourceDirName:"general",slug:"/general/ANDROID_VERSIONING",permalink:"/docs/0.3.x/general/ANDROID_VERSIONING",draft:!1,editUrl:"https://github.com/touchlab/KMMBridge/tree/main/website/versioned_docs/version-0.3.x/general/ANDROID_VERSIONING.md",tags:[],version:"0.3.x",lastUpdatedBy:"Kevin Galligan",lastUpdatedAt:1696623399,formattedLastUpdatedAt:"Oct 6, 2023",sidebarPosition:2,frontMatter:{title:"Android Versioning",sidebar_position:2},sidebar:"tutorialSidebar",previous:{title:"Configuration Overview",permalink:"/docs/0.3.x/general/CONFIGURATION_OVERVIEW"},next:{title:"Groovy Build Scripts",permalink:"/docs/0.3.x/general/GROOVY_BUILD_SCRIPTS"}},l={},u=[{value:"Using Different Versions",id:"using-different-versions",level:2},{value:"Aligning Versions",id:"aligning-versions",level:2},{value:"Auto Incrementing",id:"auto-incrementing",level:3}],d={toc:u};function c(e){let{components:n,...r}=e;return(0,i.kt)("wrapper",(0,t.Z)({},d,r,{components:n,mdxType:"MDXLayout"}),(0,i.kt)("h1",{id:"publishing-android-alongside-kmmbridge"},"Publishing Android Alongside KMMBridge"),(0,i.kt)("p",null,"KMMBridge is primarily a tool for managing KMM publications for iOS. It does not implicitly do any Android publication, but it's reasonable for a team to desire to publish both iOS and Android simultaneously. Accomplishing that simply comes down to updating your CI workflows to also run the appropriate android publish tasks. "),(0,i.kt)("p",null,"Potential problems arise, though, when you start to consider versions. KMMBridge will manage incrementing versions automatically according to the ",(0,i.kt)("a",{parentName:"p",href:"/docs/0.3.x/general/CONFIGURATION_OVERVIEW#version-managers"},"version manager that you choose in your configuration"),". "),(0,i.kt)("h2",{id:"using-different-versions"},"Using Different Versions"),(0,i.kt)("p",null,"If you don't care about the iOS and Android versions matching, then simply make sure that you ",(0,i.kt)("a",{parentName:"p",href:"/docs/0.3.x/general/CONFIGURATION_OVERVIEW#incrementing-version-managers"},"set a versionPrefix")," for KMMBridge so that you can use gradle version for Android however you like. Make sure you increment Android versioning before running your Android publish CI, or it will likely fail due to the version already being published (which is a good thing)"),(0,i.kt)("h2",{id:"aligning-versions"},"Aligning Versions"),(0,i.kt)("p",null,"If you desire the Android and iOS builds of your library to have a singular version number, you will need to turn off KMMBridge's version incrementing by using ",(0,i.kt)("a",{parentName:"p",href:"/docs/0.3.x/general/CONFIGURATION_OVERVIEW#manualversionmanager"},"manualVersionManager")," and use another mechanism for incrementing"),(0,i.kt)("pre",null,(0,i.kt)("code",{parentName:"pre",className:"language-kotlin",metastring:'title="Example config with manual version manager"',title:'"Example',config:!0,with:!0,manual:!0,version:!0,'manager"':!0},"kmmbridge {\n    mavenPublishArtifacts()\n    manualVersions()\n    spm()\n}\n")),(0,i.kt)("h3",{id:"auto-incrementing"},"Auto Incrementing"),(0,i.kt)("p",null,"There are many ways to accomplish automatic incrementing of versions utilizing tags, files, or environment variables. Once you are using ",(0,i.kt)("inlineCode",{parentName:"p"},"manualVersions()")," it's outside the purview of KMMBridge but here's a very basic example using github actions workflow run number:"),(0,i.kt)("p",null,"In your workflow, pass the run number into gradle as a property"),(0,i.kt)("pre",null,(0,i.kt)("code",{parentName:"pre",className:"language-yaml"},"-PBUILD_NUMBER=${{github.run_number}}\n")),(0,i.kt)("p",null,"In build.gradle.kts concat your build number onto the final version string:"),(0,i.kt)("pre",null,(0,i.kt)("code",{parentName:"pre",className:"language-kotlin"},'    val GROUP: String by project\n    val LIBRARY_VERSION: String by project\n    val BUILD_NUMBER: String? by project\n\n    group = GROUP\n    version = BUILD_NUMBER?.let { "$LIBRARY_VERSION.$it"} ?: "0.0.0"\n')),(0,i.kt)("p",null,"This is of course a very simplistic approach to incrementing versions that may have issues as you use the workflow. There are various plugins and prebuilt actions which you can use instead."))}c.isMDXComponent=!0}}]);