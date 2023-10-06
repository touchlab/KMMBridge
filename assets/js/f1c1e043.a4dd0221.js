"use strict";(self.webpackChunktouchlab=self.webpackChunktouchlab||[]).push([[4183],{3905:(e,t,r)=>{r.d(t,{Zo:()=>p,kt:()=>m});var a=r(7294);function n(e,t,r){return t in e?Object.defineProperty(e,t,{value:r,enumerable:!0,configurable:!0,writable:!0}):e[t]=r,e}function o(e,t){var r=Object.keys(e);if(Object.getOwnPropertySymbols){var a=Object.getOwnPropertySymbols(e);t&&(a=a.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),r.push.apply(r,a)}return r}function i(e){for(var t=1;t<arguments.length;t++){var r=null!=arguments[t]?arguments[t]:{};t%2?o(Object(r),!0).forEach((function(t){n(e,t,r[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(r)):o(Object(r)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(r,t))}))}return e}function c(e,t){if(null==e)return{};var r,a,n=function(e,t){if(null==e)return{};var r,a,n={},o=Object.keys(e);for(a=0;a<o.length;a++)r=o[a],t.indexOf(r)>=0||(n[r]=e[r]);return n}(e,t);if(Object.getOwnPropertySymbols){var o=Object.getOwnPropertySymbols(e);for(a=0;a<o.length;a++)r=o[a],t.indexOf(r)>=0||Object.prototype.propertyIsEnumerable.call(e,r)&&(n[r]=e[r])}return n}var s=a.createContext({}),l=function(e){var t=a.useContext(s),r=t;return e&&(r="function"==typeof e?e(t):i(i({},t),e)),r},p=function(e){var t=l(e.components);return a.createElement(s.Provider,{value:t},e.children)},u="mdxType",d={inlineCode:"code",wrapper:function(e){var t=e.children;return a.createElement(a.Fragment,{},t)}},f=a.forwardRef((function(e,t){var r=e.components,n=e.mdxType,o=e.originalType,s=e.parentName,p=c(e,["components","mdxType","originalType","parentName"]),u=l(r),f=n,m=u["".concat(s,".").concat(f)]||u[f]||d[f]||o;return r?a.createElement(m,i(i({ref:t},p),{},{components:r})):a.createElement(m,i({ref:t},p))}));function m(e,t){var r=arguments,n=t&&t.mdxType;if("string"==typeof e||n){var o=r.length,i=new Array(o);i[0]=f;var c={};for(var s in t)hasOwnProperty.call(t,s)&&(c[s]=t[s]);c.originalType=e,c[u]="string"==typeof e?e:n,i[1]=c;for(var l=2;l<o;l++)i[l]=r[l];return a.createElement.apply(null,i)}return a.createElement.apply(null,r)}f.displayName="MDXCreateElement"},9548:(e,t,r)=>{r.r(t),r.d(t,{assets:()=>s,contentTitle:()=>i,default:()=>u,frontMatter:()=>o,metadata:()=>c,toc:()=>l});var a=r(7462),n=(r(7294),r(3905));const o={sidebar_position:4},i="Artifactory Artifacts",c={unversionedId:"artifacts/ARTIFACTORY_ARTIFACTS",id:"artifacts/ARTIFACTORY_ARTIFACTS",title:"Artifactory Artifacts",description:"If you're using Artifactory to store artifacts, you just have to set up a repo on Artifactory and add your repo url and credentials in the",source:"@site/docs/artifacts/ARTIFACTORY_ARTIFACTS.md",sourceDirName:"artifacts",slug:"/artifacts/ARTIFACTORY_ARTIFACTS",permalink:"/docs/artifacts/ARTIFACTORY_ARTIFACTS",draft:!1,editUrl:"https://github.com/touchlab/KMMBridge/tree/main/website/docs/artifacts/ARTIFACTORY_ARTIFACTS.md",tags:[],version:"current",lastUpdatedBy:"Kevin Galligan",lastUpdatedAt:1696623399,formattedLastUpdatedAt:"Oct 6, 2023",sidebarPosition:4,frontMatter:{sidebar_position:4},sidebar:"tutorialSidebar",previous:{title:"JetBrains Space Artifacts",permalink:"/docs/artifacts/SPACE_ARTIFACTS"},next:{title:"CocoaPods",permalink:"/docs/category/cocoapods"}},s={},l=[],p={toc:l};function u(e){let{components:t,...r}=e;return(0,n.kt)("wrapper",(0,a.Z)({},p,r,{components:t,mdxType:"MDXLayout"}),(0,n.kt)("h1",{id:"artifactory-artifacts"},"Artifactory Artifacts"),(0,n.kt)("p",null,"If you're using Artifactory to store artifacts, you just have to set up a repo on Artifactory and add your repo url and credentials in the\n",(0,n.kt)("inlineCode",{parentName:"p"},"publishing")," block ",(0,n.kt)("a",{parentName:"p",href:"/docs/artifacts/MAVEN_REPO_ARTIFACTS#1-configure-push-access"},"see this example"),", as well as ",(0,n.kt)("a",{parentName:"p",href:"/docs/artifacts/MAVEN_REPO_ARTIFACTS#2-configure-client-read-access"},"configure client read access")),(0,n.kt)("p",null,"NOTE: If you add the Artifactory repo with the ",(0,n.kt)("inlineCode",{parentName:"p"},"artifactory")," gradle plugin KMMBridge won't be able to find the\nrepo. For now, you'll have to also add the repo in the maven ",(0,n.kt)("inlineCode",{parentName:"p"},"publishing")," block."),(0,n.kt)("p",null,"When publishing in a CI action, if you're using CocoaPods you need to add the credentials to ",(0,n.kt)("inlineCode",{parentName:"p"},"~/.netrc")," before running publish\nto validate the podspec. To do this simply use the custom netrc params in our GitHub Workflow."),(0,n.kt)("p",null,"You'll also need to add the username and password gradle params through the ",(0,n.kt)("inlineCode",{parentName:"p"},"gradle_params")," secret in our workflow or"),(0,n.kt)("pre",null,(0,n.kt)("code",{parentName:"pre",className:"language-yaml"},"jobs:\n  call-kmmbridge-publish:\n    uses: touchlab/KMMBridgeGithubWorkflow/.github/workflows/faktorybuildbranches.yml@v0.8\n    with: \n      netrcMachine: touchlabartifactory.jfrog.io\n    secrets:\n      PODSPEC_SSH_KEY: ${{ secrets.PODSPEC_SSH_KEY }}\n      netrcUsername: ${{ secrets.ARTIFACTORY_USERNAME }} \n      netrcPassword: ${{ secrets.ARTIFACTORY_PASSWORD }} \n      gradle_params: -PUSERNAME=${{ secrets.ARTIFACTORY_USERNAME}} -PPASSWORD=${{ secrets.ARTIFACTORY_PASSWORD }}\n\n")))}u.isMDXComponent=!0}}]);