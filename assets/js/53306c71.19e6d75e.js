"use strict";(self.webpackChunktouchlab=self.webpackChunktouchlab||[]).push([[4525],{3905:(e,t,o)=>{o.d(t,{Zo:()=>d,kt:()=>y});var n=o(7294);function r(e,t,o){return t in e?Object.defineProperty(e,t,{value:o,enumerable:!0,configurable:!0,writable:!0}):e[t]=o,e}function a(e,t){var o=Object.keys(e);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(e);t&&(n=n.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),o.push.apply(o,n)}return o}function p(e){for(var t=1;t<arguments.length;t++){var o=null!=arguments[t]?arguments[t]:{};t%2?a(Object(o),!0).forEach((function(t){r(e,t,o[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(o)):a(Object(o)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(o,t))}))}return e}function i(e,t){if(null==e)return{};var o,n,r=function(e,t){if(null==e)return{};var o,n,r={},a=Object.keys(e);for(n=0;n<a.length;n++)o=a[n],t.indexOf(o)>=0||(r[o]=e[o]);return r}(e,t);if(Object.getOwnPropertySymbols){var a=Object.getOwnPropertySymbols(e);for(n=0;n<a.length;n++)o=a[n],t.indexOf(o)>=0||Object.prototype.propertyIsEnumerable.call(e,o)&&(r[o]=e[o])}return r}var l=n.createContext({}),s=function(e){var t=n.useContext(l),o=t;return e&&(o="function"==typeof e?e(t):p(p({},t),e)),o},d=function(e){var t=s(e.components);return n.createElement(l.Provider,{value:t},e.children)},c="mdxType",u={inlineCode:"code",wrapper:function(e){var t=e.children;return n.createElement(n.Fragment,{},t)}},h=n.forwardRef((function(e,t){var o=e.components,r=e.mdxType,a=e.originalType,l=e.parentName,d=i(e,["components","mdxType","originalType","parentName"]),c=s(o),h=r,y=c["".concat(l,".").concat(h)]||c[h]||u[h]||a;return o?n.createElement(y,p(p({ref:t},d),{},{components:o})):n.createElement(y,p({ref:t},d))}));function y(e,t){var o=arguments,r=t&&t.mdxType;if("string"==typeof e||r){var a=o.length,p=new Array(a);p[0]=h;var i={};for(var l in t)hasOwnProperty.call(t,l)&&(i[l]=t[l]);i.originalType=e,i[c]="string"==typeof e?e:r,p[1]=i;for(var s=2;s<a;s++)p[s]=o[s];return n.createElement.apply(null,p)}return n.createElement.apply(null,o)}h.displayName="MDXCreateElement"},8206:(e,t,o)=>{o.r(t),o.d(t,{assets:()=>l,contentTitle:()=>p,default:()=>c,frontMatter:()=>a,metadata:()=>i,toc:()=>s});var n=o(7462),r=(o(7294),o(3905));const a={},p="Publishing Podspecs to GitHub",i={unversionedId:"cocoapods/COCOAPODS_GITHUB_PODSPEC",id:"cocoapods/COCOAPODS_GITHUB_PODSPEC",title:"Publishing Podspecs to GitHub",description:"CocoaPods supports hosting private podspec repos. These are Git repos that only host release publishing info for",source:"@site/docs/cocoapods/03_COCOAPODS_GITHUB_PODSPEC.md",sourceDirName:"cocoapods",slug:"/cocoapods/COCOAPODS_GITHUB_PODSPEC",permalink:"/docs/cocoapods/COCOAPODS_GITHUB_PODSPEC",draft:!1,editUrl:"https://github.com/touchlab/KMMBridge/tree/main/website/docs/cocoapods/03_COCOAPODS_GITHUB_PODSPEC.md",tags:[],version:"current",lastUpdatedBy:"Russell Wolf",lastUpdatedAt:1678461721,formattedLastUpdatedAt:"Mar 10, 2023",sidebarPosition:3,frontMatter:{},sidebar:"tutorialSidebar",previous:{title:"CocoaPods Local Dev Flow",permalink:"/docs/cocoapods/IOS_LOCAL_DEV_COCOAPODS"},next:{title:"Swift Package Manager (SPM)",permalink:"/docs/category/swift-package-manager-spm"}},l={},s=[{value:"Note",id:"note",level:2},{value:"Overview",id:"overview",level:2},{value:"Creating a Podspec Repo",id:"creating-a-podspec-repo",level:2},{value:"Adding the Spec Repo to Your Project",id:"adding-the-spec-repo-to-your-project",level:2},{value:"Publishing Podspecs to the Public Cocoapods Trunk",id:"publishing-podspecs-to-the-public-cocoapods-trunk",level:3},{value:"Deploy Keys",id:"deploy-keys",level:2},{value:"Create Deploy Keys",id:"create-deploy-keys",level:3},{value:"Add Deploy Key to Podspec Repo",id:"add-deploy-key-to-podspec-repo",level:3},{value:"Add Deploy Key to KMM Repo",id:"add-deploy-key-to-kmm-repo",level:3}],d={toc:s};function c(e){let{components:t,...a}=e;return(0,r.kt)("wrapper",(0,n.Z)({},d,a,{components:t,mdxType:"MDXLayout"}),(0,r.kt)("h1",{id:"publishing-podspecs-to-github"},"Publishing Podspecs to GitHub"),(0,r.kt)("p",null,"CocoaPods supports hosting private podspec repos. These are Git repos that only host release publishing info for\nCocoaPods podspecs, not their actual code. If you are planning to publish KMM Xcode Frameworks with CocoaPods, that\nmeans you'll need a separate Git repo dedicated to publishing podspec versions. See the\n",(0,r.kt)("a",{parentName:"p",href:"https://guides.cocoapods.org/making/private-cocoapods.html"},"CocoaPods Documentation")," for more context."),(0,r.kt)("h2",{id:"note"},"Note"),(0,r.kt)("p",null,"We ",(0,r.kt)("em",{parentName:"p"},"highly")," recommend publishing from CI rather than from your local machine, although if properly configured you\ncan do either. Our documentation generally assumes a CI configuration."),(0,r.kt)("h2",{id:"overview"},"Overview"),(0,r.kt)("p",null,"When using the KMMBridge CocoaPods configuration to publish to a private podspec repo,\nyou'll need to do some extra setup in your CI action to give the runner access to the podspec repo. This doc will\ngive examples for GitHub actions but the same general steps should apply to other CI setups."),(0,r.kt)("h2",{id:"creating-a-podspec-repo"},"Creating a Podspec Repo"),(0,r.kt)("p",null,"First you'll need a GitHub repo to store your remote podspecs. Simply create a new repo in GitHub and be sure to\ninitialize it with a README file. This sets up your main branch and creates an initial commit. The publish task will NOT\nwork on an empty repository that contains no commits. "),(0,r.kt)("h2",{id:"adding-the-spec-repo-to-your-project"},"Adding the Spec Repo to Your Project"),(0,r.kt)("p",null,"Once you've created a spec repo, you'll need to pass the url to KMMBridge in the configuration block. Make sure to use the\nssh url and not the http url or the CI setup described here won't work."),(0,r.kt)("pre",null,(0,r.kt)("code",{parentName:"pre",className:"language-kotlin"},'kmmbridge {\n  ...\n  cocoapods("git@github.com:<ORG>/<PODSPEC REPO>.git")\n  // NOT THIS\n  // cocoapods("https://github.com/ORG/REPO.git")\n}\n')),(0,r.kt)("h3",{id:"publishing-podspecs-to-the-public-cocoapods-trunk"},"Publishing Podspecs to the Public Cocoapods Trunk"),(0,r.kt)("p",null,"If your cocoapod will be public rather than private, you may want to publish to the\npublic ",(0,r.kt)("a",{parentName:"p",href:"https://github.com/CocoaPods/Specs"},"Cocoapods Trunk")," rather than a private spec repo. You can do this by\ncalling ",(0,r.kt)("inlineCode",{parentName:"p"},"cocoapodsTrunk()")," instead of ",(0,r.kt)("inlineCode",{parentName:"p"},"cocoapods()"),":"),(0,r.kt)("pre",null,(0,r.kt)("code",{parentName:"pre",className:"language-kotlin"},"kmmbridge {\n  ...\n  cocoapodsTrunk()\n}\n")),(0,r.kt)("p",null,"Some alternate setup is required in order to publish to Trunk. See ",(0,r.kt)("a",{parentName:"p",href:"https://guides.cocoapods.org/making/getting-setup-with-trunk.html"},"the Cocoapods documentation")," for details."),(0,r.kt)("h2",{id:"deploy-keys"},"Deploy Keys"),(0,r.kt)("h3",{id:"create-deploy-keys"},"Create Deploy Keys"),(0,r.kt)("p",null,"You'll need a deploy key to give your CI access to the spec repo. To set up a deploy key, create an ssh public/private\nkey pair on your local machine using the following command"),(0,r.kt)("p",null,(0,r.kt)("inlineCode",{parentName:"p"},'ssh-keygen -t ed25519 -f deploykey -C "git@github.com:<ORG>/<PODSPEC REPO>"')),(0,r.kt)("ul",null,(0,r.kt)("li",{parentName:"ul"},(0,r.kt)("p",{parentName:"li"},(0,r.kt)("inlineCode",{parentName:"p"},"-f deploykey")," gives a custom name ",(0,r.kt)("inlineCode",{parentName:"p"},"deploykey")," to the generated keys and will put both keys in the current directory.\nIf you run this command in your repo make sure you delete these files after finishing setup and do NOT commit them to\nyour repo.")),(0,r.kt)("li",{parentName:"ul"},(0,r.kt)("p",{parentName:"li"},(0,r.kt)("inlineCode",{parentName:"p"},'-C "git@github.com:<ORG>/<PODSPEC REPO>"')," adds the ",(0,r.kt)("strong",{parentName:"p"},(0,r.kt)("em",{parentName:"strong"},"spec repo"))," as a comment to the key that gives the ssh client a hint on when to\nuse this key. This is optional but recommended."))),(0,r.kt)("admonition",{title:"Warning",type:"caution"},(0,r.kt)("p",{parentName:"admonition"},"Generally the comment in the key is not important, but in this case, the workflow configures access to a specific repo based\non the comment. If you have failures like ",(0,r.kt)("inlineCode",{parentName:"p"},"ERROR: Repository not found.")," or ",(0,r.kt)("inlineCode",{parentName:"p"},"fatal: Could not read from remote repository."),",\nmake sure you used the spec repo and not the build repo in your comment.")),(0,r.kt)("p",null,"After running this command, your working directory will have two files: ",(0,r.kt)("inlineCode",{parentName:"p"},"deploykey"),", the private key, and ",(0,r.kt)("inlineCode",{parentName:"p"},"deploykey.pub"),", the public key.\nThe public key, ",(0,r.kt)("inlineCode",{parentName:"p"},"deploykey.pub"),", is what you will set up as a deploy key in your Podspec repo. The private key, ",(0,r.kt)("inlineCode",{parentName:"p"},"deploykey"),", will need to be added in the\nssh agent in your CI actions in order to pull/push from the spec repo. "),(0,r.kt)("h3",{id:"add-deploy-key-to-podspec-repo"},"Add Deploy Key to Podspec Repo"),(0,r.kt)("p",null,"To add ",(0,r.kt)("inlineCode",{parentName:"p"},"deploykey.pub")," to your Podspec repo, go to Settings->Deploy Keys (you'll need admin access to the repo), then click ",(0,r.kt)("em",{parentName:"p"},"Add Deploy Key")," "),(0,r.kt)("p",null,(0,r.kt)("img",{alt:"Deploy Keys",src:o(9879).Z,width:"1107",height:"548"})),(0,r.kt)("p",null,"Give your deploy key a useful title like ",(0,r.kt)("inlineCode",{parentName:"p"},"Podspec Publish Key"),", then copy the contents of ",(0,r.kt)("inlineCode",{parentName:"p"},"deploykey.pub")," into the ",(0,r.kt)("em",{parentName:"p"},"Key")," field.\nClick ",(0,r.kt)("em",{parentName:"p"},"Add key")," and your deploy key will be setup. "),(0,r.kt)("p",null,(0,r.kt)("img",{alt:"img.png",src:o(2488).Z,width:"790",height:"473"})),(0,r.kt)("h3",{id:"add-deploy-key-to-kmm-repo"},"Add Deploy Key to KMM Repo"),(0,r.kt)("p",null,"Once you have the public key of your deploy key pair, you'll need to have the private key available in order to publish from\nCI. "),(0,r.kt)("p",null,"In the repo for the KMM code you want to publish, go to Settings -> Secrets -> Actions (you'll need repo admin)."),(0,r.kt)("p",null,(0,r.kt)("img",{alt:"img_1.png",src:o(4693).Z,width:"1125",height:"624"})),(0,r.kt)("p",null,"Click ",(0,r.kt)("em",{parentName:"p"},"Add Secret")," and name your secret ",(0,r.kt)("inlineCode",{parentName:"p"},"PODSPEC_SSH_KEY"),". You must match this secret name exactly for the KMMBridge GitHub\nworkflow to work properly."),(0,r.kt)("p",null,"Paste the entire contents of ",(0,r.kt)("inlineCode",{parentName:"p"},"deploykey")," into the ",(0,r.kt)("em",{parentName:"p"},"Secrets")," field then click ",(0,r.kt)("em",{parentName:"p"},"Add Secret")," "),(0,r.kt)("p",null,(0,r.kt)("img",{alt:"img.png",src:o(7408).Z,width:"794",height:"404"})),(0,r.kt)("p",null,"Once these keys are added, you can use the KMMBridge workflow to handle adding the key to the ssh agent and calling\nthe publish task to push a remote podspec to your podspec repo. For a more manual workflow setup, see ",(0,r.kt)("a",{parentName:"p",href:"https://github.com/touchlab/KMMBridgeGithubWorkflow/blob/f6075b60151caf15b8759c811b0d2458fbdd08a7/.github/workflows/faktorybuild.yml#L21"},"this section"),"\nin our workflow to see how the deploy key is used."))}c.isMDXComponent=!0},2488:(e,t,o)=>{o.d(t,{Z:()=>n});const n=o.p+"assets/images/add_key-06d9028a4b9e0c9bd576c8911766dc7e.png"},7408:(e,t,o)=>{o.d(t,{Z:()=>n});const n=o.p+"assets/images/add_secret_ssh-80fb52f59496eae55196376a10858c6a.png"},9879:(e,t,o)=>{o.d(t,{Z:()=>n});const n=o.p+"assets/images/deploykey-a391c29d3eb4402c9f13beff0340274d.png"},4693:(e,t,o)=>{o.d(t,{Z:()=>n});const n=o.p+"assets/images/settings_secrets-93b46f3bff6fc6102d20b469ca39cee1.png"}}]);