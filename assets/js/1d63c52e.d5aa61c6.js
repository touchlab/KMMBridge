"use strict";(self.webpackChunktouchlab=self.webpackChunktouchlab||[]).push([[4446],{3905:(e,t,a)=>{a.d(t,{Zo:()=>u,kt:()=>g});var n=a(7294);function r(e,t,a){return t in e?Object.defineProperty(e,t,{value:a,enumerable:!0,configurable:!0,writable:!0}):e[t]=a,e}function i(e,t){var a=Object.keys(e);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(e);t&&(n=n.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),a.push.apply(a,n)}return a}function o(e){for(var t=1;t<arguments.length;t++){var a=null!=arguments[t]?arguments[t]:{};t%2?i(Object(a),!0).forEach((function(t){r(e,t,a[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(a)):i(Object(a)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(a,t))}))}return e}function s(e,t){if(null==e)return{};var a,n,r=function(e,t){if(null==e)return{};var a,n,r={},i=Object.keys(e);for(n=0;n<i.length;n++)a=i[n],t.indexOf(a)>=0||(r[a]=e[a]);return r}(e,t);if(Object.getOwnPropertySymbols){var i=Object.getOwnPropertySymbols(e);for(n=0;n<i.length;n++)a=i[n],t.indexOf(a)>=0||Object.prototype.propertyIsEnumerable.call(e,a)&&(r[a]=e[a])}return r}var l=n.createContext({}),p=function(e){var t=n.useContext(l),a=t;return e&&(a="function"==typeof e?e(t):o(o({},t),e)),a},u=function(e){var t=p(e.components);return n.createElement(l.Provider,{value:t},e.children)},c="mdxType",d={inlineCode:"code",wrapper:function(e){var t=e.children;return n.createElement(n.Fragment,{},t)}},h=n.forwardRef((function(e,t){var a=e.components,r=e.mdxType,i=e.originalType,l=e.parentName,u=s(e,["components","mdxType","originalType","parentName"]),c=p(a),h=r,g=c["".concat(l,".").concat(h)]||c[h]||d[h]||i;return a?n.createElement(g,o(o({ref:t},u),{},{components:a})):n.createElement(g,o({ref:t},u))}));function g(e,t){var a=arguments,r=t&&t.mdxType;if("string"==typeof e||r){var i=a.length,o=new Array(i);o[0]=h;var s={};for(var l in t)hasOwnProperty.call(t,l)&&(s[l]=t[l]);s.originalType=e,s[c]="string"==typeof e?e:r,o[1]=s;for(var p=2;p<i;p++)o[p]=a[p];return n.createElement.apply(null,o)}return n.createElement.apply(null,a)}h.displayName="MDXCreateElement"},9073:(e,t,a)=>{a.r(t),a.d(t,{assets:()=>l,contentTitle:()=>o,default:()=>c,frontMatter:()=>i,metadata:()=>s,toc:()=>p});var n=a(7462),r=(a(7294),a(3905));const i={sidebar_position:1},o="Maven Repository Artifacts",s={unversionedId:"artifacts/MAVEN_REPO_ARTIFACTS",id:"version-0.3.x/artifacts/MAVEN_REPO_ARTIFACTS",title:"Maven Repository Artifacts",description:'You can use a "standard" Maven repository to store your KMMBridge zip artifact. This will push the XCFramework zip archive to a standard maven/gradle endpoint. Using this artifact storage method allows for a wider range of publication possibilities, and utilizes standard Gradle publishing config.',source:"@site/versioned_docs/version-0.3.x/artifacts/MAVEN_REPO_ARTIFACTS.md",sourceDirName:"artifacts",slug:"/artifacts/MAVEN_REPO_ARTIFACTS",permalink:"/docs/0.3.x/artifacts/MAVEN_REPO_ARTIFACTS",draft:!1,editUrl:"https://github.com/touchlab/KMMBridge/tree/main/website/versioned_docs/version-0.3.x/artifacts/MAVEN_REPO_ARTIFACTS.md",tags:[],version:"0.3.x",lastUpdatedBy:"Kevin Galligan",lastUpdatedAt:1725391153,formattedLastUpdatedAt:"Sep 3, 2024",sidebarPosition:1,frontMatter:{sidebar_position:1},sidebar:"tutorialSidebar",previous:{title:"Artifact Managers",permalink:"/docs/0.3.x/category/artifact-managers"},next:{title:"S3 Public Artifacts",permalink:"/docs/0.3.x/artifacts/S3_PUBLIC_ARTIFACTS"}},l={},p=[{value:"1) Configure Push Access",id:"1-configure-push-access",level:2},{value:"2) Configure Client Read Access",id:"2-configure-client-read-access",level:2},{value:"Package Metadata",id:"package-metadata",level:3},{value:"Binary Access",id:"binary-access",level:3},{value:"3) Publish Versions and Operations",id:"3-publish-versions-and-operations",level:2},{value:"Github Packages",id:"github-packages",level:2}],u={toc:p};function c(e){let{components:t,...a}=e;return(0,r.kt)("wrapper",(0,n.Z)({},u,a,{components:t,mdxType:"MDXLayout"}),(0,r.kt)("h1",{id:"maven-repository-artifacts"},"Maven Repository Artifacts"),(0,r.kt)("p",null,'You can use a "standard" Maven repository to store your KMMBridge zip artifact. This will push the XCFramework zip archive to a standard maven/gradle endpoint. Using this artifact storage method allows for a wider range of publication possibilities, and utilizes standard Gradle publishing config.'),(0,r.kt)("p",null,"Using Maven/Gradle repos to store your KMMBridge artifacts requires a few setup steps, and has some unique considerations. Setup requires handling the following:"),(0,r.kt)("ol",null,(0,r.kt)("li",{parentName:"ol"},"Configure push access to your repository."),(0,r.kt)("li",{parentName:"ol"},"Configure artifact read access for private repos (generally the ",(0,r.kt)("inlineCode",{parentName:"li"},"~/.netrc")," or Mac Keychain Access config)."),(0,r.kt)("li",{parentName:"ol"},"(Possibly) consolidate your publishing versions and operations.")),(0,r.kt)("h2",{id:"1-configure-push-access"},"1) Configure Push Access"),(0,r.kt)("p",null,"Each repo type has it's own unique setup, but essentially we need:"),(0,r.kt)("ol",null,(0,r.kt)("li",{parentName:"ol"},"A repo url"),(0,r.kt)("li",{parentName:"ol"},"A username that can publish to the repo"),(0,r.kt)("li",{parentName:"ol"},"A password/token for that user")),(0,r.kt)("p",null,"See specific guidance for Github Packages, JetBrains Space, and Artifactory."),(0,r.kt)("p",null,"The standard Gradle publishing config looks like the following."),(0,r.kt)("pre",null,(0,r.kt)("code",{parentName:"pre",className:"language-kotlin"},'plugins {\n    `maven-publish`\n    // etc\n}\n\n// ...\n\npublishing {\n    // Externally set these values. Generally ~/.gradle/gradle.properties or CI Secrets\n    val publishUsername: String by project\n    val publishPassword: String by project\n    repositories {\n        maven {\n            url = uri("https://someservice/path/to/repo")\n            credentials {\n                username = publishUsername\n                password = publishPassword\n            }\n        }\n    }\n}\n')),(0,r.kt)("p",null,"With publishing configured, you can publish everything with ",(0,r.kt)("inlineCode",{parentName:"p"},"./gradlew publish"),", or just publish the KMMBridge artifact with ",(0,r.kt)("inlineCode",{parentName:"p"},"./gradlew kmmBridgePublish"),"."),(0,r.kt)("h2",{id:"2-configure-client-read-access"},"2) Configure Client Read Access"),(0,r.kt)("p",null,"To access your published Xcode Framework package, you'll need to make sure Xcode has access to the package metadata and the binary itself. The package metadata differs depending on if you use Cocoapods or Swift Pacakge Manager. Access to the binary is the same with either."),(0,r.kt)("h3",{id:"package-metadata"},"Package Metadata"),(0,r.kt)("p",null,"Cocoapods publishes pacakge metadata to a git repo. See ",(0,r.kt)("a",{parentName:"p",href:"../cocoapods/IOS_COCOAPODS"},"CocoaPods setup"),"."),(0,r.kt)("p",null,"SPM uses git for publishing and git tags for versioning. See ",(0,r.kt)("a",{parentName:"p",href:"../spm/IOS_SPM"},"SPM setup"),"."),(0,r.kt)("h3",{id:"binary-access"},"Binary Access"),(0,r.kt)("p",null,"If the binaries are stored in a private server, you'll need to configure auth access to them. You can do that with ",(0,r.kt)("inlineCode",{parentName:"p"},"~/.netrc")," or Mac Keychain Access. We'll use ",(0,r.kt)("inlineCode",{parentName:"p"},"~/.netrc")," here."),(0,r.kt)("p",null,"Open or create ",(0,r.kt)("inlineCode",{parentName:"p"},"~/.netrc"),". Add the server and credentials to access your files."),(0,r.kt)("pre",null,(0,r.kt)("code",{parentName:"pre",className:"language-shell"},"machine repo.example.com\n  login <user>\n  password <pass>\n")),(0,r.kt)("p",null,"If you're using GitHub Packages, you'll need your GitHub username and a Personal Access Token that can read repo and read packages."),(0,r.kt)("pre",null,(0,r.kt)("code",{parentName:"pre",className:"language-shell"},"machine maven.pkg.github.com\n  login <Github User>\n  password <PAT>\n")),(0,r.kt)("p",null,"For different repos you'll need to change the machine to point at the server where your files are kept."),(0,r.kt)("h2",{id:"3-publish-versions-and-operations"},"3) Publish Versions and Operations"),(0,r.kt)("p",null,"The Maven publishing adds a special artifact to the \"standard\" Gradle publishing config, specically for KMMBridge. We're publishing the Xcode Framework XCFramework zip file as it's own Gradle/Maven artifact. If you run ",(0,r.kt)("inlineCode",{parentName:"p"},"./gradlew publish"),", it'll attempt to publish KMMBridge along with any other configured publish targets."),(0,r.kt)("p",null,"If you do intend to publish everything, you'll likely want to disable the automatically incrementing verison support that KMMBridge provides, and use the standard Gradle publishing version management."),(0,r.kt)("p",null,"If you want to use the version from Gradle, use the following configuration:"),(0,r.kt)("pre",null,(0,r.kt)("code",{parentName:"pre",className:"language-kotlin"},"kmmbridge {\n    mavenPublishArtifacts()\n    manualVersions()\n    // etc\n}\n")),(0,r.kt)("p",null,(0,r.kt)("inlineCode",{parentName:"p"},"manualVersions()")," means we're just going to use whatever version string is available and not try to auto-increment. If you don't provide a ",(0,r.kt)("inlineCode",{parentName:"p"},"versionPrefix")," value, KMMBridge will just use the version provided to Gradle."),(0,r.kt)("h2",{id:"github-packages"},"Github Packages"),(0,r.kt)("p",null,"If you are publishing to Github Packages and you are using our ",(0,r.kt)("a",{parentName:"p",href:"https://github.com/touchlab/KMMBridgeGithubWorkflow"},"helper workflow")," with GitHub Actions, you can turn on publishing to GitHub Packages very easily with the following function at the root of your Gradle build file."),(0,r.kt)("p",null,"Pretty much everything you'd need to configure to publish to GitHub Packages is listed here."),(0,r.kt)("pre",null,(0,r.kt)("code",{parentName:"pre",className:"language-kotlin"},'import co.touchlab.faktory.addGithubPackagesRepository\n\nplugins {\n    kotlin("multiplatform")\n    `maven-publish`\n    id("co.touchlab.faktory.kmmbridge") version "{{VERSION_NAME_3X}}"\n}\n\ngroup = "co.touchlab.somegroup"\nversion = "0.2"\n\nkotlin {\n    ios {\n        binaries.framework()\n    }\n}\n\nkmmbridge {\n    mavenPublishArtifacts() // <- Publish using a Maven repo\n    gitTagVersions()\n    spm()\n}\n\naddGithubPackagesRepository() // <- Add the GitHub Packages repo\n')),(0,r.kt)("p",null,"The ",(0,r.kt)("inlineCode",{parentName:"p"},"plugins")," section, ",(0,r.kt)("inlineCode",{parentName:"p"},"group/version"),", and ",(0,r.kt)("inlineCode",{parentName:"p"},"kotlin")," blocks should looks pretty familiar to anybody who has done KMP/KMM work. You'll probably have more targets in ",(0,r.kt)("inlineCode",{parentName:"p"},"kotlin"),", and probably more entries in ",(0,r.kt)("inlineCode",{parentName:"p"},"plugins"),", but this setup would be complete and functional as presented."),(0,r.kt)("p",null,"In ",(0,r.kt)("inlineCode",{parentName:"p"},"kmmbridge"),", notice ",(0,r.kt)("inlineCode",{parentName:"p"},"mavenPublishArtifacts()")," tells the plugin to push KMMBridge artifacts to a Maven repo. You then need to define a repo. Rather than do everything manually, you can just call ",(0,r.kt)("inlineCode",{parentName:"p"},"addGithubPackagesRepository()"),", which will add the correct repo given parameters that are passed in from GitHub Actions."),(0,r.kt)("p",null,"To publish to Github Packages through Github Actions, your workflow needs to run with package write permissions, and it needs access to repo contents in order to commit updates like the Package.swift file. You can add ",(0,r.kt)("inlineCode",{parentName:"p"},"permissions: write-all")," at the top-level in your workflow file to accomplish this, or a more granular setting:"),(0,r.kt)("pre",null,(0,r.kt)("code",{parentName:"pre",className:"language-yaml"},"permissions:\n  contents: write\n  packages: write\n")),(0,r.kt)("p",null,"See the ",(0,r.kt)("a",{parentName:"p",href:"https://docs.github.com/en/actions/using-jobs/assigning-permissions-to-jobs"},"Github Actions documentation")," for more details on permission settings."))}c.isMDXComponent=!0}}]);