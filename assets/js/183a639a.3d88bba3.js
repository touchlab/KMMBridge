"use strict";(self.webpackChunkmy_website_ts=self.webpackChunkmy_website_ts||[]).push([[301],{3905:function(e,t,n){n.d(t,{Zo:function(){return p},kt:function(){return h}});var o=n(7294);function i(e,t,n){return t in e?Object.defineProperty(e,t,{value:n,enumerable:!0,configurable:!0,writable:!0}):e[t]=n,e}function l(e,t){var n=Object.keys(e);if(Object.getOwnPropertySymbols){var o=Object.getOwnPropertySymbols(e);t&&(o=o.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),n.push.apply(n,o)}return n}function r(e){for(var t=1;t<arguments.length;t++){var n=null!=arguments[t]?arguments[t]:{};t%2?l(Object(n),!0).forEach((function(t){i(e,t,n[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(n)):l(Object(n)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(n,t))}))}return e}function a(e,t){if(null==e)return{};var n,o,i=function(e,t){if(null==e)return{};var n,o,i={},l=Object.keys(e);for(o=0;o<l.length;o++)n=l[o],t.indexOf(n)>=0||(i[n]=e[n]);return i}(e,t);if(Object.getOwnPropertySymbols){var l=Object.getOwnPropertySymbols(e);for(o=0;o<l.length;o++)n=l[o],t.indexOf(n)>=0||Object.prototype.propertyIsEnumerable.call(e,n)&&(i[n]=e[n])}return i}var s=o.createContext({}),u=function(e){var t=o.useContext(s),n=t;return e&&(n="function"==typeof e?e(t):r(r({},t),e)),n},p=function(e){var t=u(e.components);return o.createElement(s.Provider,{value:t},e.children)},d={inlineCode:"code",wrapper:function(e){var t=e.children;return o.createElement(o.Fragment,{},t)}},c=o.forwardRef((function(e,t){var n=e.components,i=e.mdxType,l=e.originalType,s=e.parentName,p=a(e,["components","mdxType","originalType","parentName"]),c=u(n),h=i,m=c["".concat(s,".").concat(h)]||c[h]||d[h]||l;return n?o.createElement(m,r(r({ref:t},p),{},{components:n})):o.createElement(m,r({ref:t},p))}));function h(e,t){var n=arguments,i=t&&t.mdxType;if("string"==typeof e||i){var l=n.length,r=new Array(l);r[0]=c;var a={};for(var s in t)hasOwnProperty.call(t,s)&&(a[s]=t[s]);a.originalType=e,a.mdxType="string"==typeof e?e:i,r[1]=a;for(var u=2;u<l;u++)r[u]=n[u];return o.createElement.apply(null,r)}return o.createElement.apply(null,n)}c.displayName="MDXCreateElement"},8656:function(e,t,n){n.r(t),n.d(t,{assets:function(){return p},contentTitle:function(){return s},default:function(){return h},frontMatter:function(){return a},metadata:function(){return u},toc:function(){return d}});var o=n(7462),i=n(3366),l=(n(7294),n(3905)),r=["components"],a={sidebar_position:3,title:"Default GitHub Workflow"},s="Default GitHub Flow",u={unversionedId:"DEFAULT_GITHUB_FLOW",id:"DEFAULT_GITHUB_FLOW",title:"Default GitHub Workflow",description:"If you are hosting your repos in GitHub and can use GitHub Actions for CI, the Default GitHub Flow is the simplest way to start publishing Kotlin builds for iOS.",source:"@site/docs/DEFAULT_GITHUB_FLOW.md",sourceDirName:".",slug:"/DEFAULT_GITHUB_FLOW",permalink:"/KMMBridge/docs/DEFAULT_GITHUB_FLOW",draft:!1,editUrl:"https://github.com/touchlab/KMMBridge/tree/main/website/docs/DEFAULT_GITHUB_FLOW.md",tags:[],version:"current",lastUpdatedBy:"Kevin Galligan",lastUpdatedAt:1665362437,formattedLastUpdatedAt:"10/10/2022",sidebarPosition:3,frontMatter:{sidebar_position:3,title:"Default GitHub Workflow"},sidebar:"tutorialSidebar",previous:{title:"KMMBridge Intro",permalink:"/KMMBridge/docs/intro"},next:{title:"iOS Dev Setup",permalink:"/KMMBridge/docs/IOS_DEV_SETUP"}},p={},d=[{value:"Overview",id:"overview",level:2},{value:"Kotlin Repo",id:"kotlin-repo",level:2},{value:"Spec Repo",id:"spec-repo",level:2},{value:"Configure The Kotlin Repo",id:"configure-the-kotlin-repo",level:2},{value:"1 Access the Gradle plugin",id:"1-access-the-gradle-plugin",level:3},{value:"2 Modify the Gradle Build",id:"2-modify-the-gradle-build",level:3},{value:"3 Add the GitHub Actions workflow call",id:"3-add-the-github-actions-workflow-call",level:3},{value:"4 Add and push your code",id:"4-add-and-push-your-code",level:3},{value:"Publish a Build!",id:"publish-a-build",level:2},{value:"Next Steps",id:"next-steps",level:2},{value:"See Also",id:"see-also",level:2}],c={toc:d};function h(e){var t=e.components,n=(0,i.Z)(e,r);return(0,l.kt)("wrapper",(0,o.Z)({},c,n,{components:t,mdxType:"MDXLayout"}),(0,l.kt)("h1",{id:"default-github-flow"},"Default GitHub Flow"),(0,l.kt)("p",null,"If you are hosting your repos in GitHub and can use GitHub Actions for CI, the Default GitHub Flow is the simplest way to start publishing Kotlin builds for iOS."),(0,l.kt)("h2",{id:"overview"},"Overview"),(0,l.kt)("p",null,"This flow allows you to do the following:"),(0,l.kt)("ul",null,(0,l.kt)("li",{parentName:"ul"},"Publish to public or private repos"),(0,l.kt)("li",{parentName:"ul"},"Upload Xcode Framework artifacts to GitHub releases. No external storage or auth configuration is required."),(0,l.kt)("li",{parentName:"ul"},"Can use either CocoaPods, SPM, or both")),(0,l.kt)("h2",{id:"kotlin-repo"},"Kotlin Repo"),(0,l.kt)("p",null,"You'll need find or add the Kotlin Multiplatform module to publish. This module can be in the same project as your Android code (if any), or in a separate repo. In the Kotlin repo you'll add the Gradle config and CI to publish Xcode Frameworks."),(0,l.kt)("h2",{id:"spec-repo"},"Spec Repo"),(0,l.kt)("p",null,"If you are going to publish for CocoaPods, you'll also need a CocoaPods spec repo. This is a separate repo that CocoaPods uses to store published version information. This config is somewhat more complex, but still reasonably straightforward to configure."),(0,l.kt)("h2",{id:"configure-the-kotlin-repo"},"Configure The Kotlin Repo"),(0,l.kt)("h3",{id:"1-access-the-gradle-plugin"},"1 Access the Gradle plugin"),(0,l.kt)("p",null,"Make sure you have ",(0,l.kt)("inlineCode",{parentName:"p"},"mavenCental()")," set up for Gradle plugins. That means adding it to the ",(0,l.kt)("inlineCode",{parentName:"p"},"pluginManagement")," or ",(0,l.kt)("inlineCode",{parentName:"p"},"buildscript")," blocks. In ",(0,l.kt)("inlineCode",{parentName:"p"},"settings.gradle.kts"),":"),(0,l.kt)("pre",null,(0,l.kt)("code",{parentName:"pre",className:"language-kotlin"},"pluginManagement {\n    repositories {\n        gradlePluginPortal()\n        mavenCentral()\n    }\n}\n")),(0,l.kt)("p",null,"Note: If you're using a SNAPSHOT version of the plugin, add the SNAPSHOT repo as well:"),(0,l.kt)("pre",null,(0,l.kt)("code",{parentName:"pre",className:"language-kotlin"},'pluginManagement {\n    repositories {\n        gradlePluginPortal()\n        mavenCentral()\n        maven("https://oss.sonatype.org/content/repositories/snapshots")\n    }\n}\n')),(0,l.kt)("h3",{id:"2-modify-the-gradle-build"},"2 Modify the Gradle Build"),(0,l.kt)("p",null,"Find the ",(0,l.kt)("inlineCode",{parentName:"p"},"build.gradle.kts")," file where you configure the multiplatform module you'd like to publish. Add the KMMBridge Gradle plugin:"),(0,l.kt)("pre",null,(0,l.kt)("code",{parentName:"pre",className:"language-kotlin"},'plugins {\n    kotlin("multiplatform")\n    id("co.touchlab.faktory.kmmbridge") version "0.2.2"\n}\n')),(0,l.kt)("p",null,"Later in the same file, add the ",(0,l.kt)("inlineCode",{parentName:"p"},"kmmbridge")," config block:"),(0,l.kt)("pre",null,(0,l.kt)("code",{parentName:"pre",className:"language-kotlin"},'kmmbridge {\n    githubReleaseArtifacts()\n    githubReleaseVersions()\n    spm()\n    cocoapods("git@github.com:touchlab/PodSpecs.git")\n    versionPrefix.set("0.3")\n}\n')),(0,l.kt)("p",null,(0,l.kt)("inlineCode",{parentName:"p"},"githubReleaseArtifacts()")," is mandatory for this flow. Without that, files will not be published anywhere (there are other publishing options available)."),(0,l.kt)("p",null,(0,l.kt)("inlineCode",{parentName:"p"},"githubReleaseVersions()")," is highly recommended. This will use GitHub releases for release tracking and incrementing. You can use a different version manager, but you need to configure one. See: ",(0,l.kt)("a",{parentName:"p",href:"/KMMBridge/docs/general/CONFIGURATION_OVERVIEW#version-managers"},"Version Managers")," for more detail."),(0,l.kt)("p",null,(0,l.kt)("inlineCode",{parentName:"p"},"spm()")," only needs to be added if you want to support SPM. The parameter points at the root directory of your repo. In this case, we have the KMP module in a folder under the repo, so the repo root is one level up. This is where your ",(0,l.kt)("inlineCode",{parentName:"p"},"Package.swift")," file should be stored."),(0,l.kt)("p",null,"Note: this config is only for SPM publishing. To understand how to integrate an SPM build into Xcode, and how to locally build and test Kotlin changes, see ",(0,l.kt)("a",{parentName:"p",href:"/KMMBridge/docs/spm/IOS_SPM"},"IOS_SPM"),"."),(0,l.kt)("p",null,(0,l.kt)("inlineCode",{parentName:"p"},'cocoapods("[some git repo].git")')," is only needed if you plan to publish for CocoaPods. You will need the spec repo mentioned above, properly configured for deployment. See  ",(0,l.kt)("a",{parentName:"p",href:"/KMMBridge/docs/cocoapods/COCOAPODS_GITHUB_PODSPEC"},"COCOAPODS_GITHUB_PODSPEC")," for details on getting the podspec repo configured."),(0,l.kt)("p",null,(0,l.kt)("inlineCode",{parentName:"p"},"versionPrefix")," is not technically necessary but highly encouraged. As you publish builds, the semantic version number will be incremented and appended onto the prefix. So, in the example above, the first version would be ",(0,l.kt)("inlineCode",{parentName:"p"},"0.3.0"),", next ",(0,l.kt)("inlineCode",{parentName:"p"},"0.3.1"),", and so on."),(0,l.kt)("h3",{id:"3-add-the-github-actions-workflow-call"},"3 Add the GitHub Actions workflow call"),(0,l.kt)("p",null,"At the top of your project, if it does not already exist, add the folders ",(0,l.kt)("inlineCode",{parentName:"p"},".github/workflows"),". Add a file called ",(0,l.kt)("inlineCode",{parentName:"p"},"kmmbridgepnblish.yml")," there, and copy the following into it."),(0,l.kt)("pre",null,(0,l.kt)("code",{parentName:"pre",className:"language-yaml"},"name: KMMBridge Publish Release\non: workflow_dispatch\n\njobs:\n  call-kmmbridge-publish:\n    uses: touchlab/KMMBridgeGithubWorkflow/.github/workflows/faktorybuild.yml@main\n")),(0,l.kt)("p",null,"Note: if you are using CocoaPods and a podspec repo, your file should look like the following:"),(0,l.kt)("pre",null,(0,l.kt)("code",{parentName:"pre",className:"language-yaml"},"name: KMMBridge Publish Release\non: workflow_dispatch\n\njobs:\n  call-kmmbridge-publish:\n    uses: touchlab/KMMBridgeGithubWorkflow/.github/workflows/faktorybuild.yml@main\n    secrets:\n      PODSPEC_SSH_KEY: ${{ secrets.PODSPEC_SSH_KEY }}\n")),(0,l.kt)("p",null,"You need to pass the ssh key configured earlier."),(0,l.kt)("p",null,"There are actually 2 versions of the workflow script. When using SPM, the config file ",(0,l.kt)("inlineCode",{parentName:"p"},"Package.swift")," gets updated to reflect the published url file. If pushed to the main branch, that will force you to pull the latest and mergh when trying to update the code later. It can also result in a conflict for that file. As an alternative, you can use ",(0,l.kt)("inlineCode",{parentName:"p"},"faktorybuildbranches.yml"),":"),(0,l.kt)("pre",null,(0,l.kt)("code",{parentName:"pre",className:"language-yaml"},"name: KMMBridge Publish Release\non: workflow_dispatch\n\njobs:\n  call-kmmbridge-publish:\n    uses: touchlab/KMMBridgeGithubWorkflow/.github/workflows/faktorybuildbranches.yml@main\n")),(0,l.kt)("p",null,"That script will publish builds to a new branch (named with a random UUID), which will allow publishing new versions without potential file conflicts."),(0,l.kt)("h3",{id:"4-add-and-push-your-code"},"4 Add and push your code"),(0,l.kt)("p",null,"Push your changes to GitHub, and make sure they're in the default branch."),(0,l.kt)("h2",{id:"publish-a-build"},"Publish a Build!"),(0,l.kt)("p",null,'Assuming your configuration is set up correctly, you should be able to publish your first build. In the Kotlin repo\'s GitHub home page, go to "Actions", select "KMMBridge Publish Release", and manually run it.'),(0,l.kt)("p",null,(0,l.kt)("img",{parentName:"p",src:"https://tl-navigator-images.s3.us-east-1.amazonaws.com/docimages/2022-10-04_21-14-runbuild.png",alt:"runbuild"})),(0,l.kt)("p",null,"When that run is complete, you should see a green result. If not, please reach out :) This sample project is very small. A larger project may take considerably longer to build, so be prepared to wait..."),(0,l.kt)("p",null,(0,l.kt)("img",{parentName:"p",src:"https://tl-navigator-images.s3.us-east-1.amazonaws.com/docimages/2022-10-04_21-19-image-20221004211903511.png",alt:"image-20221004211903511"})),(0,l.kt)("h2",{id:"next-steps"},"Next Steps"),(0,l.kt)("p",null,"You'll want to pull this new build into Xcode. For more information on how to do that, see  ",(0,l.kt)("a",{parentName:"p",href:"/KMMBridge/docs/IOS_DEV_SETUP"},"IOS_DEV_SETUP"),"."),(0,l.kt)("h2",{id:"see-also"},"See Also"),(0,l.kt)("p",null,(0,l.kt)("a",{parentName:"p",href:"/KMMBridge/docs/TROUBLESHOOTING"},"TROUBLESHOOTING")))}h.isMDXComponent=!0}}]);