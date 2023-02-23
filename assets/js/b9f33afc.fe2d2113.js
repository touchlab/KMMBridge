"use strict";(self.webpackChunktouchlab=self.webpackChunktouchlab||[]).push([[256],{3905:(e,t,r)=>{r.d(t,{Zo:()=>u,kt:()=>d});var a=r(7294);function n(e,t,r){return t in e?Object.defineProperty(e,t,{value:r,enumerable:!0,configurable:!0,writable:!0}):e[t]=r,e}function i(e,t){var r=Object.keys(e);if(Object.getOwnPropertySymbols){var a=Object.getOwnPropertySymbols(e);t&&(a=a.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),r.push.apply(r,a)}return r}function o(e){for(var t=1;t<arguments.length;t++){var r=null!=arguments[t]?arguments[t]:{};t%2?i(Object(r),!0).forEach((function(t){n(e,t,r[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(r)):i(Object(r)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(r,t))}))}return e}function s(e,t){if(null==e)return{};var r,a,n=function(e,t){if(null==e)return{};var r,a,n={},i=Object.keys(e);for(a=0;a<i.length;a++)r=i[a],t.indexOf(r)>=0||(n[r]=e[r]);return n}(e,t);if(Object.getOwnPropertySymbols){var i=Object.getOwnPropertySymbols(e);for(a=0;a<i.length;a++)r=i[a],t.indexOf(r)>=0||Object.prototype.propertyIsEnumerable.call(e,r)&&(n[r]=e[r])}return n}var l=a.createContext({}),c=function(e){var t=a.useContext(l),r=t;return e&&(r="function"==typeof e?e(t):o(o({},t),e)),r},u=function(e){var t=c(e.components);return a.createElement(l.Provider,{value:t},e.children)},p="mdxType",f={inlineCode:"code",wrapper:function(e){var t=e.children;return a.createElement(a.Fragment,{},t)}},b=a.forwardRef((function(e,t){var r=e.components,n=e.mdxType,i=e.originalType,l=e.parentName,u=s(e,["components","mdxType","originalType","parentName"]),p=c(r),b=n,d=p["".concat(l,".").concat(b)]||p[b]||f[b]||i;return r?a.createElement(d,o(o({ref:t},u),{},{components:r})):a.createElement(d,o({ref:t},u))}));function d(e,t){var r=arguments,n=t&&t.mdxType;if("string"==typeof e||n){var i=r.length,o=new Array(i);o[0]=b;var s={};for(var l in t)hasOwnProperty.call(t,l)&&(s[l]=t[l]);s.originalType=e,s[p]="string"==typeof e?e:n,o[1]=s;for(var c=2;c<i;c++)o[c]=r[c];return a.createElement.apply(null,o)}return a.createElement.apply(null,r)}b.displayName="MDXCreateElement"},5618:(e,t,r)=>{r.r(t),r.d(t,{assets:()=>l,contentTitle:()=>o,default:()=>p,frontMatter:()=>i,metadata:()=>s,toc:()=>c});var a=r(7462),n=(r(7294),r(3905));const i={sidebar_position:2},o="S3 Public Artifacts",s={unversionedId:"artifacts/S3_PUBLIC_ARTIFACTS",id:"artifacts/S3_PUBLIC_ARTIFACTS",title:"S3 Public Artifacts",description:"You can publish artifacts to AWS S3. However, there is no easy way to make them private using auth options available out of the box for S3. Therefore, artifacts in S3 will have publicly readable URL's. Those URL's are generated with a random UUID, so they're essentially unguessable, but that's an important restriction.",source:"@site/docs/artifacts/S3_PUBLIC_ARTIFACTS.md",sourceDirName:"artifacts",slug:"/artifacts/S3_PUBLIC_ARTIFACTS",permalink:"/KMMBridge/artifacts/S3_PUBLIC_ARTIFACTS",draft:!1,editUrl:"https://github.com/touchlab/KMMBridge/tree/main/website/docs/artifacts/S3_PUBLIC_ARTIFACTS.md",tags:[],version:"current",lastUpdatedBy:"Russell Wolf",lastUpdatedAt:1677179061,formattedLastUpdatedAt:"Feb 23, 2023",sidebarPosition:2,frontMatter:{sidebar_position:2},sidebar:"tutorialSidebar",previous:{title:"Maven Repository Artifacts",permalink:"/KMMBridge/artifacts/MAVEN_REPO_ARTIFACTS"},next:{title:"JetBrains Space Artifacts",permalink:"/KMMBridge/artifacts/SPACE_ARTIFACTS"}},l={},c=[{value:"Configuration",id:"configuration",level:2},{value:"Considerations",id:"considerations",level:2}],u={toc:c};function p(e){let{components:t,...r}=e;return(0,n.kt)("wrapper",(0,a.Z)({},u,r,{components:t,mdxType:"MDXLayout"}),(0,n.kt)("h1",{id:"s3-public-artifacts"},"S3 Public Artifacts"),(0,n.kt)("p",null,"You can publish artifacts to AWS S3. However, there is no easy way to make them private using auth options available out of the box for S3. Therefore, artifacts in S3 will have publicly readable URL's. Those URL's are generated with a random UUID, so they're essentially unguessable, but that's an important restriction."),(0,n.kt)("p",null,"Some environments have S3 access automatically secured thorugh VPN's, etc. If you have thoughts on how to implement the S3 Artifact Manager in private environments, please reach out."),(0,n.kt)("h2",{id:"configuration"},"Configuration"),(0,n.kt)("pre",null,(0,n.kt)("code",{parentName:"pre",className:"language-kotlin"},'kmmbridge {\n    s3PublicArtifacts(\n        "us\u2011east\u20111",\n        "my-kmm-artifacts",\n        "[ACCESS_KEY]",\n        "[SECRET_ACCESS_KEY]"\n    )\n}\n')),(0,n.kt)("p",null,"Parameters:"),(0,n.kt)("ul",null,(0,n.kt)("li",{parentName:"ul"},"region: the AWS region"),(0,n.kt)("li",{parentName:"ul"},"bucket: the S3 bucket"),(0,n.kt)("li",{parentName:"ul"},"accessKeyId: IAM access key (should probably be a repo secret)"),(0,n.kt)("li",{parentName:"ul"},"secretAccessKey: IAM secret key (should ",(0,n.kt)("em",{parentName:"li"},"definitely")," be a repo secret)"),(0,n.kt)("li",{parentName:"ul"},"makeArtifactsPublic: optional boolean. Defaults to true. Can keep URL's private, but out of the box, there is no way to authenticate clients for access."),(0,n.kt)("li",{parentName:"ul"},"altBaseUrl: optional alternative base URL.")),(0,n.kt)("h2",{id:"considerations"},"Considerations"),(0,n.kt)("p",null,"We generally wouldn't use this option unless we're publishing a public repo. However, if you have alternative ways of securing and authenticating an S3 bucket, this is a good option."))}p.isMDXComponent=!0}}]);