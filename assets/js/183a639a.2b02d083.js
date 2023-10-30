"use strict";(self.webpackChunktouchlab=self.webpackChunktouchlab||[]).push([[3301],{3905:(e,t,a)=>{a.d(t,{Zo:()=>p,kt:()=>m});var r=a(7294);function n(e,t,a){return t in e?Object.defineProperty(e,t,{value:a,enumerable:!0,configurable:!0,writable:!0}):e[t]=a,e}function i(e,t){var a=Object.keys(e);if(Object.getOwnPropertySymbols){var r=Object.getOwnPropertySymbols(e);t&&(r=r.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),a.push.apply(a,r)}return a}function l(e){for(var t=1;t<arguments.length;t++){var a=null!=arguments[t]?arguments[t]:{};t%2?i(Object(a),!0).forEach((function(t){n(e,t,a[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(a)):i(Object(a)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(a,t))}))}return e}function o(e,t){if(null==e)return{};var a,r,n=function(e,t){if(null==e)return{};var a,r,n={},i=Object.keys(e);for(r=0;r<i.length;r++)a=i[r],t.indexOf(a)>=0||(n[a]=e[a]);return n}(e,t);if(Object.getOwnPropertySymbols){var i=Object.getOwnPropertySymbols(e);for(r=0;r<i.length;r++)a=i[r],t.indexOf(a)>=0||Object.prototype.propertyIsEnumerable.call(e,a)&&(n[a]=e[a])}return n}var s=r.createContext({}),u=function(e){var t=r.useContext(s),a=t;return e&&(a="function"==typeof e?e(t):l(l({},t),e)),a},p=function(e){var t=u(e.components);return r.createElement(s.Provider,{value:t},e.children)},d="mdxType",c={inlineCode:"code",wrapper:function(e){var t=e.children;return r.createElement(r.Fragment,{},t)}},h=r.forwardRef((function(e,t){var a=e.components,n=e.mdxType,i=e.originalType,s=e.parentName,p=o(e,["components","mdxType","originalType","parentName"]),d=u(a),h=n,m=d["".concat(s,".").concat(h)]||d[h]||c[h]||i;return a?r.createElement(m,l(l({ref:t},p),{},{components:a})):r.createElement(m,l({ref:t},p))}));function m(e,t){var a=arguments,n=t&&t.mdxType;if("string"==typeof e||n){var i=a.length,l=new Array(i);l[0]=h;var o={};for(var s in t)hasOwnProperty.call(t,s)&&(o[s]=t[s]);o.originalType=e,o[d]="string"==typeof e?e:n,l[1]=o;for(var u=2;u<i;u++)l[u]=a[u];return r.createElement.apply(null,l)}return r.createElement.apply(null,a)}h.displayName="MDXCreateElement"},8656:(e,t,a)=>{a.r(t),a.d(t,{assets:()=>s,contentTitle:()=>l,default:()=>d,frontMatter:()=>i,metadata:()=>o,toc:()=>u});var r=a(7462),n=(a(7294),a(3905));const i={sidebar_position:3,title:"Default GitHub Workflow"},l="GitHub Actions Workflow",o={unversionedId:"DEFAULT_GITHUB_FLOW",id:"DEFAULT_GITHUB_FLOW",title:"Default GitHub Workflow",description:"We publish a reference CI implementation that works with GitHub Actions and, by default, GitHub Packages. This",source:"@site/docs/DEFAULT_GITHUB_FLOW.md",sourceDirName:".",slug:"/DEFAULT_GITHUB_FLOW",permalink:"/docs/DEFAULT_GITHUB_FLOW",draft:!1,editUrl:"https://github.com/touchlab/KMMBridge/tree/main/website/docs/DEFAULT_GITHUB_FLOW.md",tags:[],version:"current",lastUpdatedBy:"Kevin Galligan",lastUpdatedAt:1698631967,formattedLastUpdatedAt:"Oct 30, 2023",sidebarPosition:3,frontMatter:{sidebar_position:3,title:"Default GitHub Workflow"},sidebar:"tutorialSidebar",previous:{title:"What Are We Doing?",permalink:"/docs/WHAT_ARE_WE_DOING"},next:{title:"iOS Dev Setup",permalink:"/docs/IOS_DEV_SETUP"}},s={},u=[{value:"Overview",id:"overview",level:2},{value:"Workflow Reference",id:"workflow-reference",level:2},{value:"Inputs",id:"inputs",level:3},{value:"Secrets",id:"secrets",level:3},{value:"Steps",id:"steps",level:3},{value:"Checkout the repo with tags",id:"checkout-the-repo-with-tags",level:4},{value:"Read versionBaseProperty",id:"read-versionbaseproperty",level:4},{value:"Print versionBaseProperty",id:"print-versionbaseproperty",level:4},{value:"Calculate Next Version",id:"calculate-next-version",level:4},{value:"Print Next Version",id:"print-next-version",level:4},{value:"Build Version Tag Marker",id:"build-version-tag-marker",level:4},{value:"Create Build Branch",id:"create-build-branch",level:4},{value:"Apply SSH Key",id:"apply-ssh-key",level:4},{value:"Apply netrc values",id:"apply-netrc-values",level:4},{value:"Setup Java",id:"setup-java",level:4},{value:"Validate Gradle Wrapper/Cache build tooling",id:"validate-gradle-wrappercache-build-tooling",level:4},{value:"Build Main",id:"build-main",level:4},{value:"Finish Release",id:"finish-release",level:4},{value:"Build Version Tag Marker Cleanup",id:"build-version-tag-marker-cleanup",level:4},{value:"Delete branch",id:"delete-branch",level:4},{value:"Next Steps",id:"next-steps",level:2}],p={toc:u};function d(e){let{components:t,...a}=e;return(0,n.kt)("wrapper",(0,r.Z)({},p,a,{components:t,mdxType:"MDXLayout"}),(0,n.kt)("h1",{id:"github-actions-workflow"},"GitHub Actions Workflow"),(0,n.kt)("p",null,"We publish a reference CI implementation that works with GitHub Actions and, by default, GitHub Packages. This\nimplementation is the simplest way to get a new project set up if your team uses GitHUb. It is also a great reference to\nreview if you are setting up KMMBridge with a different CI or artifact host."),(0,n.kt)("p",null,"The main workflow is here:"),(0,n.kt)("github",{org:"touchlab",repo:"KMMBridgeGithubWorkflow"}),(0,n.kt)("p",null,"There are also a set of GitHub Action steps used in the workflow above that can be used in custom workflows. You can\nfind these listed below."),(0,n.kt)("admonition",{type:"note"},(0,n.kt)("p",{parentName:"admonition"},"This flow depends on calling our GitHub Actions workflow. Many of the features of KMMBridge assume operations that the\nGitHub Actions workflow is performing. SPM in particular uses git repo structure and tags to manage versions. Our GitHub\nactions and workflow will modify your repo by adding tags and creating build branches. If you want complete control over\nthis, you'll need to fork and modify our processes.")),(0,n.kt)("p",null,"You can see our GitHub Workflow in action by checking\nout ",(0,n.kt)("a",{parentName:"p",href:"https://touchlab.co/kmmbridge-quick-start"},"KMMBridge Quick Start Updates"),"."),(0,n.kt)("h2",{id:"overview"},"Overview"),(0,n.kt)("p",null,"This flow allows you to do the following:"),(0,n.kt)("ul",null,(0,n.kt)("li",{parentName:"ul"},"Publish to public or private repos."),(0,n.kt)("li",{parentName:"ul"},"Uses Maven artifacts with GitHub Packages. Standard tools!"),(0,n.kt)("li",{parentName:"ul"},"Upload Xcode Framework artifacts to GitHub Packages. No external storage or auth configuration is required. All auth\nis manages through GitHub."),(0,n.kt)("li",{parentName:"ul"},"Can use either CocoaPods, SPM, or both."),(0,n.kt)("li",{parentName:"ul"},"Publish iOS and (optionally) Android binaries.")),(0,n.kt)("h2",{id:"workflow-reference"},"Workflow Reference"),(0,n.kt)("h3",{id:"inputs"},"Inputs"),(0,n.kt)("table",null,(0,n.kt)("thead",{parentName:"table"},(0,n.kt)("tr",{parentName:"thead"},(0,n.kt)("th",{parentName:"tr",align:null},"Key"),(0,n.kt)("th",{parentName:"tr",align:null},"Type"),(0,n.kt)("th",{parentName:"tr",align:null},"Required?"),(0,n.kt)("th",{parentName:"tr",align:null},"Default"),(0,n.kt)("th",{parentName:"tr",align:null},"Description"))),(0,n.kt)("tbody",{parentName:"table"},(0,n.kt)("tr",{parentName:"tbody"},(0,n.kt)("td",{parentName:"tr",align:null},"module"),(0,n.kt)("td",{parentName:"tr",align:null},"string"),(0,n.kt)("td",{parentName:"tr",align:null},"No"),(0,n.kt)("td",{parentName:"tr",align:null},"N/A"),(0,n.kt)("td",{parentName:"tr",align:null},"The module name to run the task on if you have multiple kmp modules. Leave this blank to have all KMMBridge modules build")),(0,n.kt)("tr",{parentName:"tbody"},(0,n.kt)("td",{parentName:"tr",align:null},"publishTask"),(0,n.kt)("td",{parentName:"tr",align:null},"string"),(0,n.kt)("td",{parentName:"tr",align:null},"No"),(0,n.kt)("td",{parentName:"tr",align:null},"'kmmBridgePublish'"),(0,n.kt)("td",{parentName:"tr",align:null},"If you need to call something other than 'kmmBridgePublish', pass it in here.")),(0,n.kt)("tr",{parentName:"tbody"},(0,n.kt)("td",{parentName:"tr",align:null},"netrcMachine"),(0,n.kt)("td",{parentName:"tr",align:null},"string"),(0,n.kt)("td",{parentName:"tr",align:null},"No"),(0,n.kt)("td",{parentName:"tr",align:null},"'maven.pkg.github.com'"),(0,n.kt)("td",{parentName:"tr",align:null},"The domain name of the maching for netrc config.")),(0,n.kt)("tr",{parentName:"tbody"},(0,n.kt)("td",{parentName:"tr",align:null},"jvmVersion"),(0,n.kt)("td",{parentName:"tr",align:null},"string"),(0,n.kt)("td",{parentName:"tr",align:null},"No"),(0,n.kt)("td",{parentName:"tr",align:null},"'11'"),(0,n.kt)("td",{parentName:"tr",align:null},"JVM Version to use. You may want to use '17' for current Android compatibility.")),(0,n.kt)("tr",{parentName:"tbody"},(0,n.kt)("td",{parentName:"tr",align:null},"runsOn"),(0,n.kt)("td",{parentName:"tr",align:null},"string"),(0,n.kt)("td",{parentName:"tr",align:null},"No"),(0,n.kt)("td",{parentName:"tr",align:null},"'macos-12'"),(0,n.kt)("td",{parentName:"tr",align:null},"GitHub Actions host name. The default will likely change as GitHub Actions changes versions.")),(0,n.kt)("tr",{parentName:"tbody"},(0,n.kt)("td",{parentName:"tr",align:null},"versionBaseProperty"),(0,n.kt)("td",{parentName:"tr",align:null},"string"),(0,n.kt)("td",{parentName:"tr",align:null},"Yes"),(0,n.kt)("td",{parentName:"tr",align:null},"N/A"),(0,n.kt)("td",{parentName:"tr",align:null},"The GitHub Workflow calculates versions automatically using a base (ex. ",(0,n.kt)("inlineCode",{parentName:"td"},"2.3"),") and incrementing sequentially. The Workflow reads the base from a named value in ",(0,n.kt)("inlineCode",{parentName:"td"},"github.properties"),".")),(0,n.kt)("tr",{parentName:"tbody"},(0,n.kt)("td",{parentName:"tr",align:null},"retainBuildBranch"),(0,n.kt)("td",{parentName:"tr",align:null},"boolean"),(0,n.kt)("td",{parentName:"tr",align:null},"No"),(0,n.kt)("td",{parentName:"tr",align:null},"false"),(0,n.kt)("td",{parentName:"tr",align:null},"The Workflow needs to add and commit values to the repo. It does this in a build branch, which is deleted at the end. If set to 'true', this branch is retained.")))),(0,n.kt)("h3",{id:"secrets"},"Secrets"),(0,n.kt)("table",null,(0,n.kt)("thead",{parentName:"table"},(0,n.kt)("tr",{parentName:"thead"},(0,n.kt)("th",{parentName:"tr",align:null},"Key"),(0,n.kt)("th",{parentName:"tr",align:null},"Required?"),(0,n.kt)("th",{parentName:"tr",align:null},"Default"),(0,n.kt)("th",{parentName:"tr",align:null},"Description"))),(0,n.kt)("tbody",{parentName:"table"},(0,n.kt)("tr",{parentName:"tbody"},(0,n.kt)("td",{parentName:"tr",align:null},"gradle_params"),(0,n.kt)("td",{parentName:"tr",align:null},"No"),(0,n.kt)("td",{parentName:"tr",align:null},"N/A"),(0,n.kt)("td",{parentName:"tr",align:null},"Extra parameters you can send to the main Gradle call.")),(0,n.kt)("tr",{parentName:"tbody"},(0,n.kt)("td",{parentName:"tr",align:null},"PODSPEC_SSH_KEY"),(0,n.kt)("td",{parentName:"tr",align:null},"No"),(0,n.kt)("td",{parentName:"tr",align:null},"N/A"),(0,n.kt)("td",{parentName:"tr",align:null},"For publishing to CocoaPod Podspec repos.")),(0,n.kt)("tr",{parentName:"tbody"},(0,n.kt)("td",{parentName:"tr",align:null},"netrcUsername"),(0,n.kt)("td",{parentName:"tr",align:null},"No"),(0,n.kt)("td",{parentName:"tr",align:null},"'cirunner'"),(0,n.kt)("td",{parentName:"tr",align:null},"Username for netrc config.")),(0,n.kt)("tr",{parentName:"tbody"},(0,n.kt)("td",{parentName:"tr",align:null},"netrcPassword"),(0,n.kt)("td",{parentName:"tr",align:null},"No"),(0,n.kt)("td",{parentName:"tr",align:null},"secrets.GITHUB_TOKEN"),(0,n.kt)("td",{parentName:"tr",align:null},"Password for netrc config.")))),(0,n.kt)("h3",{id:"steps"},"Steps"),(0,n.kt)("h4",{id:"checkout-the-repo-with-tags"},"Checkout the repo with tags"),(0,n.kt)("p",null,"Checkout the repo, make sure we get all tags (needed for SPM versioning)."),(0,n.kt)("h4",{id:"read-versionbaseproperty"},"Read versionBaseProperty"),(0,n.kt)("p",null,"Uses a GitHub Action ",(0,n.kt)("a",{parentName:"p",href:"https://github.com/touchlab/read-property"},"touchlab/read-property")," to read a property\nfrom ",(0,n.kt)("inlineCode",{parentName:"p"},"gradle.properties"),"."),(0,n.kt)("h4",{id:"print-versionbaseproperty"},"Print versionBaseProperty"),(0,n.kt)("p",null,"You can see the value read in the GitHub Actions log (for debugging purposes)."),(0,n.kt)("h4",{id:"calculate-next-version"},"Calculate Next Version"),(0,n.kt)("p",null,"GitHub Action ",(0,n.kt)("a",{parentName:"p",href:"https://github.com/touchlab/autoversion-nextversion"},"touchlab/autoversion-nextversion"),"\nuses ",(0,n.kt)("inlineCode",{parentName:"p"},"versionBase"),", reads git tags from the repo, then calculates the next incremental build version to use."),(0,n.kt)("h4",{id:"print-next-version"},"Print Next Version"),(0,n.kt)("p",null,"Prints the output of the last call (for debugging purposes). This is the version that the workflow will attempt to\npublish."),(0,n.kt)("h4",{id:"build-version-tag-marker"},"Build Version Tag Marker"),(0,n.kt)("p",null,"GitHub Action ",(0,n.kt)("a",{parentName:"p",href:"https://github.com/touchlab/autoversion-tagmarker"},"touchlab/autoversion-tagmarker"),". During setup, this\nwill apply a temporary git tag and push to the repo remote. The purpose is to mark that version string as \"in progress\",\nbut it uses a prefix for the tag so SPM clients don't try to update the version before it's ready."),(0,n.kt)("p",null,"For example, if the next version should be ",(0,n.kt)("inlineCode",{parentName:"p"},"2.3.14"),", a marker tag of ",(0,n.kt)("inlineCode",{parentName:"p"},"autoversion-tmp-publishing-2.3.14")," will be added."),(0,n.kt)("p",null,'The reason for this marker tag is, for maven repos, including GitHub Packages, we need to "publish" the XCFramework zip\nbundle before we can publish to the dependencies (CocoaPods specifically). If the publishing process fails after the zip\nfile is put into GitHub Packages, the artifacts will still exist. If the next publish attempt uses ',(0,n.kt)("inlineCode",{parentName:"p"},"2.3.14")," again, it\nwill fail when it attempts to push the artifacts because they already exist."),(0,n.kt)("p",null,"We avoid that with the marker tag. If ",(0,n.kt)("inlineCode",{parentName:"p"},"2.3.14")," fails, and another publish attempt is made, it will use ",(0,n.kt)("inlineCode",{parentName:"p"},"2.3.15")," instead."),(0,n.kt)("h4",{id:"create-build-branch"},"Create Build Branch"),(0,n.kt)("p",null,"GitHub Action ",(0,n.kt)("a",{parentName:"p",href:"https://github.com/touchlab/autoversion-buildbranch"},"touchlab/autoversion-buildbranch"),". Very simple.\nCreates a build branch for the publication process."),(0,n.kt)("h4",{id:"apply-ssh-key"},"Apply SSH Key"),(0,n.kt)("p",null,"If you are publishing to CocoaPods, and have provided a ",(0,n.kt)("inlineCode",{parentName:"p"},"PODSPEC_SSH_KEY")," secret, it will be applied to the CI machine\nto allow publishing and access."),(0,n.kt)("h4",{id:"apply-netrc-values"},"Apply netrc values"),(0,n.kt)("p",null,"For local machine access to the published artifacts. Necessary for CocoaPods publishing. Defaults configured for GitHub Packages."),(0,n.kt)("h4",{id:"setup-java"},"Setup Java"),(0,n.kt)("p",null,"For Gradle build. Defaults to 11 currently, but will probably bump to 17 soon as current Android requires."),(0,n.kt)("h4",{id:"validate-gradle-wrappercache-build-tooling"},"Validate Gradle Wrapper/Cache build tooling"),(0,n.kt)("p",null,"Gradle and KMP build stuff."),(0,n.kt)("h4",{id:"build-main"},"Build Main"),(0,n.kt)("p",null,"Runs ",(0,n.kt)("inlineCode",{parentName:"p"},"gradlew")," to do the main build and publishing work."),(0,n.kt)("p",null,"The optional ",(0,n.kt)("inlineCode",{parentName:"p"},"module")," parameter is used to narrow the build. ",(0,n.kt)("inlineCode",{parentName:"p"},"publishTask")," is the Gradle task that's run. This can be multiple. See the reference app ",(0,n.kt)("a",{parentName:"p",href:"https://touchlab.co/kmmbridge-quick-start"},"KMMBridge Quick Start Updates"),". The secret ",(0,n.kt)("inlineCode",{parentName:"p"},"gradle_params")," is also applied here, in case your build needs something custom added."),(0,n.kt)("p",null,"Of note, the Gradle property ",(0,n.kt)("inlineCode",{parentName:"p"},"AUTO_VERSION"),' is created here and is available inside your build file. The value is the calculated version from the "Calculate Next Version" step. Inside your build script, ',(0,n.kt)("inlineCode",{parentName:"p"},"AUTO_VERSION")," should be assigned to Gradle ",(0,n.kt)("inlineCode",{parentName:"p"},"version")," is it is available. See the template project from ",(0,n.kt)("a",{parentName:"p",href:"https://touchlab.co/kmmbridge-quick-start"},"KMMBridge Quick Start Updates")," for an example."),(0,n.kt)("admonition",{type:"caution"},(0,n.kt)("p",{parentName:"admonition"},"If publishing is going to fail, it is almost certainly in this step. If you get past here, we're essentially just wrapping up.")),(0,n.kt)("h4",{id:"finish-release"},"Finish Release"),(0,n.kt)("p",null,"GitHub Action ",(0,n.kt)("a",{parentName:"p",href:"https://github.com/touchlab/autoversion-finishrelease"},"touchlab/autoversion-finishrelease"),". Adds and commits ",(0,n.kt)("inlineCode",{parentName:"p"},"Package.swift"),", adds the final version tag, and pushes everything. After this step, any SPM client can get the new version. "),(0,n.kt)("h4",{id:"build-version-tag-marker-cleanup"},"Build Version Tag Marker Cleanup"),(0,n.kt)("p",null,"GitHub Action ",(0,n.kt)("a",{parentName:"p",href:"https://github.com/touchlab/autoversion-tagmarker"},"touchlab/autoversion-tagmarker"),". Run again, but to clean up the tag markers created previously. If there were failed builds previously with the same version prefix, this action will remove all of them."),(0,n.kt)("h4",{id:"delete-branch"},"Delete branch"),(0,n.kt)("p",null,"Deletes the build branch if ",(0,n.kt)("inlineCode",{parentName:"p"},"retainBuildBranch")," is false."),(0,n.kt)("h2",{id:"next-steps"},"Next Steps"),(0,n.kt)("p",null,"There are a number of setup steps required to use this workflow. See ",(0,n.kt)("a",{parentName:"p",href:"https://touchlab.co/kmmbridge-quick-start"},"KMMBridge Quick Start Updates")," for a walkthrough of setting up a project."))}d.isMDXComponent=!0}}]);