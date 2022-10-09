import React from 'react';
import DocSidebarItemCategory from '@theme/DocSidebarItem/Category';
import DocSidebarItemLink from '@theme/DocSidebarItem/Link';
import DocSidebarItemHtml from '@theme/DocSidebarItem/Html';
export default function DocSidebarItem({item, ...props}) {
  switch (item.type) {
    case 'category':
      if (item.label.includes("All Docs")) {
        return <></>
      } else {
        return <DocSidebarItemCategory item={item} {...props} />;
      }

    case 'html':
      return <DocSidebarItemHtml item={item} {...props} />;

    case 'link':
    default:
      if (item.href.includes("alldocs")) {
        return <></>
      } else {
        return <DocSidebarItemLink item={item} {...props} />;
      }
  }
}
