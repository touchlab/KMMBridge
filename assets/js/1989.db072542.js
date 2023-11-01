"use strict";(self.webpackChunktouchlab=self.webpackChunktouchlab||[]).push([[1989],{1989:(e,t,n)=>{n.r(t),n.d(t,{default:()=>U});var a=n(7294),l=n(6010),r=n(1944),o=n(5281),c=n(3320),i=n(2802),s=n(4477),d=n(1116),m=n(1868),u=n(5999),b=n(2466),p=n(5936);const E="backToTopButton_sjWU",h="backToTopButtonShow_xfvO";function f(){const{shown:e,scrollToTop:t}=function(e){let{threshold:t}=e;const[n,l]=(0,a.useState)(!1),r=(0,a.useRef)(!1),{startScroll:o,cancelScroll:c}=(0,b.Ct)();return(0,b.RF)(((e,n)=>{let{scrollY:a}=e;const o=n?.scrollY;o&&(r.current?r.current=!1:a>=o?(c(),l(!1)):a<t?l(!1):a+window.innerHeight<document.documentElement.scrollHeight&&l(!0))})),(0,p.S)((e=>{e.location.hash&&(r.current=!0,l(!1))})),{shown:n,scrollToTop:()=>o(0)}}({threshold:300});return a.createElement("button",{"aria-label":(0,u.I)({id:"theme.BackToTopButton.buttonAriaLabel",message:"Scroll back to top",description:"The ARIA label for the back to top button"}),className:(0,l.Z)("clean-btn",o.k.common.backToTopButton,E,e&&h),type:"button",onClick:t})}var g=n(6775),v=n(7524),_=n(1825),k=n(3102),C=n(2961),S=n(5469);const I=e=>{let{sidebar:t,path:n}=e;const r=(0,C.e)();return a.createElement("ul",{className:(0,l.Z)(o.k.docs.docSidebarMenu,"menu__list")},a.createElement(S.Z,{items:t,activePath:n,onItemClick:e=>{"category"===e.type&&e.href&&r.toggle(),"link"===e.type&&r.toggle()},level:1}))};function Z(e){return a.createElement(k.Zo,{component:I,props:e})}const x=a.memo(Z);function y(e){const t=(0,v.i)(),n="desktop"===t||"ssr"===t,l="mobile"===t;return a.createElement(a.Fragment,null,n&&a.createElement(_.Z,e),l&&a.createElement(x,e))}var N=n(6023);const T="expandButton_m80_",w="expandButtonIcon_BlDH";function L(e){let{toggleSidebar:t}=e;return a.createElement("div",{className:T,title:(0,u.I)({id:"theme.docs.sidebar.expandButtonTitle",message:"Expand sidebar",description:"The ARIA label and title attribute for expand button of doc sidebar"}),"aria-label":(0,u.I)({id:"theme.docs.sidebar.expandButtonAriaLabel",message:"Expand sidebar",description:"The ARIA label and title attribute for expand button of doc sidebar"}),tabIndex:0,role:"button",onKeyDown:t,onClick:t},a.createElement(N.Z,{className:w}))}const M={docSidebarContainer:"docSidebarContainer_b6E3",docSidebarContainerHidden:"docSidebarContainerHidden_b3ry",sidebarViewport:"sidebarViewport_Xe31"};function A(e){let{children:t}=e;const n=(0,d.V)();return a.createElement(a.Fragment,{key:n?.name??"noSidebar"},t)}function B(e){let{sidebar:t,hiddenSidebarContainer:n,setHiddenSidebarContainer:r}=e;const{pathname:c}=(0,g.TH)(),[i,s]=(0,a.useState)(!1),d=(0,a.useCallback)((()=>{i&&s(!1),r((e=>!e))}),[r,i]);return a.createElement("aside",{className:(0,l.Z)(o.k.docs.docSidebarContainer,M.docSidebarContainer,n&&M.docSidebarContainerHidden),onTransitionEnd:e=>{e.currentTarget.classList.contains(M.docSidebarContainer)&&n&&s(!0)}},a.createElement(A,null,a.createElement("div",{className:(0,l.Z)(M.sidebarViewport,i&&M.sidebarViewportHidden)},a.createElement(y,{sidebar:t,path:c,onCollapse:d,isHidden:i}),i&&a.createElement(L,{toggleSidebar:d}))))}const H={docMainContainer:"docMainContainer_gTbr",docMainContainerEnhanced:"docMainContainerEnhanced_Uz_u",docItemWrapperEnhanced:"docItemWrapperEnhanced_czyv"};function D(e){let{hiddenSidebarContainer:t,children:n}=e;const r=(0,d.V)();return a.createElement("main",{className:(0,l.Z)(H.docMainContainer,(t||!r)&&H.docMainContainerEnhanced)},a.createElement("div",{className:(0,l.Z)("container padding-top--md padding-bottom--lg",H.docItemWrapper,t&&H.docItemWrapperEnhanced)},n))}const F="docPage__5DB",P="docsWrapper_BCFX";function V(e){let{children:t}=e;const n=(0,d.V)(),[l,r]=(0,a.useState)(!1);return a.createElement(m.Z,{wrapperClassName:P},a.createElement(f,null),a.createElement("div",{className:F},n&&a.createElement(B,{sidebar:n.items,hiddenSidebarContainer:l,setHiddenSidebarContainer:r}),a.createElement(D,{hiddenSidebarContainer:l},t)))}var W=n(4204),z=n(197);function R(e){const{versionMetadata:t}=e;return a.createElement(a.Fragment,null,a.createElement(z.Z,{version:t.version,tag:(0,c.os)(t.pluginId,t.version)}),a.createElement(r.d,null,t.noIndex&&a.createElement("meta",{name:"robots",content:"noindex, nofollow"})))}function U(e){const{versionMetadata:t}=e,n=(0,i.hI)(e);if(!n)return a.createElement(W.default,null);const{docElement:c,sidebarName:m,sidebarItems:u}=n;return a.createElement(a.Fragment,null,a.createElement(R,e),a.createElement(r.FG,{className:(0,l.Z)(o.k.wrapper.docsPages,o.k.page.docsDocPage,e.versionMetadata.className)},a.createElement(s.q,{version:t},a.createElement(d.b,{name:m,items:u},a.createElement(V,null,c)))))}},5469:(e,t,n)=>{n.d(t,{Z:()=>N});var a=n(7462),l=n(7294),r=n(902);const o=Symbol("EmptyContext"),c=l.createContext(o);function i(e){let{children:t}=e;const[n,a]=(0,l.useState)(null),r=(0,l.useMemo)((()=>({expandedItem:n,setExpandedItem:a})),[n]);return l.createElement(c.Provider,{value:r},t)}var s=n(6010),d=n(6668),m=n(6043),u=n(5281),b=n(2802),p=n(8596),E=n(9960),h=n(5999),f=n(2389);function g(e){let{categoryLabel:t,onClick:n}=e;return l.createElement("button",{"aria-label":(0,h.I)({id:"theme.DocSidebarItem.toggleCollapsedCategoryAriaLabel",message:"Toggle the collapsible sidebar category '{label}'",description:"The ARIA label to toggle the collapsible sidebar category"},{label:t}),type:"button",className:"clean-btn menu__caret",onClick:n})}function v(e){let{item:t,onItemClick:n,activePath:i,level:h,index:v,..._}=e;const{items:k,label:C,collapsible:S,className:I,href:Z}=t,{docs:{sidebar:{autoCollapseCategories:x}}}=(0,d.L)(),y=function(e){const t=(0,f.Z)();return(0,l.useMemo)((()=>e.href?e.href:!t&&e.collapsible?(0,b.Wl)(e):void 0),[e,t])}(t),T=(0,b._F)(t,i),w=(0,p.Mg)(Z,i),{collapsed:L,setCollapsed:M}=(0,m.u)({initialState:()=>!!S&&(!T&&t.collapsed)}),{expandedItem:A,setExpandedItem:B}=function(){const e=(0,l.useContext)(c);if(e===o)throw new r.i6("DocSidebarItemsExpandedStateProvider");return e}(),H=function(e){void 0===e&&(e=!L),B(e?null:v),M(e)};return function(e){let{isActive:t,collapsed:n,updateCollapsed:a}=e;const o=(0,r.D9)(t);(0,l.useEffect)((()=>{t&&!o&&n&&a(!1)}),[t,o,n,a])}({isActive:T,collapsed:L,updateCollapsed:H}),(0,l.useEffect)((()=>{S&&null!=A&&A!==v&&x&&M(!0)}),[S,A,v,M,x]),l.createElement("li",{className:(0,s.Z)(u.k.docs.docSidebarItemCategory,u.k.docs.docSidebarItemCategoryLevel(h),"menu__list-item",{"menu__list-item--collapsed":L},I)},l.createElement("div",{className:(0,s.Z)("menu__list-item-collapsible",{"menu__list-item-collapsible--active":w})},l.createElement(E.Z,(0,a.Z)({className:(0,s.Z)("menu__link",{"menu__link--sublist":S,"menu__link--sublist-caret":!Z&&S,"menu__link--active":T}),onClick:S?e=>{n?.(t),Z?H(!1):(e.preventDefault(),H())}:()=>{n?.(t)},"aria-current":w?"page":void 0,"aria-expanded":S?!L:void 0,href:S?y??"#":y},_),C),Z&&S&&l.createElement(g,{categoryLabel:C,onClick:e=>{e.preventDefault(),H()}})),l.createElement(m.z,{lazy:!0,as:"ul",className:"menu__list",collapsed:L},l.createElement(N,{items:k,tabIndex:L?-1:0,onItemClick:n,activePath:i,level:h+1})))}var _=n(3919),k=n(9471);const C="menuExternalLink_NmtK";function S(e){let{item:t,onItemClick:n,activePath:r,level:o,index:c,...i}=e;const{href:d,label:m,className:p,autoAddBaseUrl:h}=t,f=(0,b._F)(t,r),g=(0,_.Z)(d);return l.createElement("li",{className:(0,s.Z)(u.k.docs.docSidebarItemLink,u.k.docs.docSidebarItemLinkLevel(o),"menu__list-item",p),key:m},l.createElement(E.Z,(0,a.Z)({className:(0,s.Z)("menu__link",!g&&C,{"menu__link--active":f}),autoAddBaseUrl:h,"aria-current":f?"page":void 0,to:d},g&&{onClick:n?()=>n(t):void 0},i),m,!g&&l.createElement(k.Z,null)))}const I="menuHtmlItem_M9Kj";function Z(e){let{item:t,level:n,index:a}=e;const{value:r,defaultStyle:o,className:c}=t;return l.createElement("li",{className:(0,s.Z)(u.k.docs.docSidebarItemLink,u.k.docs.docSidebarItemLinkLevel(n),o&&[I,"menu__list-item"],c),key:a,dangerouslySetInnerHTML:{__html:r}})}function x(e){let{item:t,...n}=e;switch(t.type){case"category":return l.createElement(v,(0,a.Z)({item:t},n));case"html":return l.createElement(Z,(0,a.Z)({item:t},n));default:return l.createElement(S,(0,a.Z)({item:t},n))}}function y(e){let{items:t,...n}=e;return l.createElement(i,null,t.map(((e,t)=>l.createElement(x,(0,a.Z)({key:t,item:e,index:t},n)))))}const N=(0,l.memo)(y)},6023:(e,t,n)=>{n.d(t,{Z:()=>r});var a=n(7462),l=n(7294);function r(e){return l.createElement("svg",(0,a.Z)({width:"20",height:"20","aria-hidden":"true"},e),l.createElement("g",{fill:"#7a7a7a"},l.createElement("path",{d:"M9.992 10.023c0 .2-.062.399-.172.547l-4.996 7.492a.982.982 0 01-.828.454H1c-.55 0-1-.453-1-1 0-.2.059-.403.168-.551l4.629-6.942L.168 3.078A.939.939 0 010 2.528c0-.548.45-.997 1-.997h2.996c.352 0 .649.18.828.45L9.82 9.472c.11.148.172.347.172.55zm0 0"}),l.createElement("path",{d:"M19.98 10.023c0 .2-.058.399-.168.547l-4.996 7.492a.987.987 0 01-.828.454h-3c-.547 0-.996-.453-.996-1 0-.2.059-.403.168-.551l4.625-6.942-4.625-6.945a.939.939 0 01-.168-.55 1 1 0 01.996-.997h3c.348 0 .649.18.828.45l4.996 7.492c.11.148.168.347.168.55zm0 0"})))}}}]);