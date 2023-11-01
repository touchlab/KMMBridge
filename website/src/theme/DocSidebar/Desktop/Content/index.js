import React, {useState} from 'react';
import clsx from 'clsx';
import {ThemeClassNames} from '@docusaurus/theme-common';
import {
    useAnnouncementBar,
    useScrollPosition,
} from '@docusaurus/theme-common/internal';
import {translate} from '@docusaurus/Translate';
import DocSidebarItems from '@theme/DocSidebarItems';
import styles from './styles.module.css';

function useShowAnnouncementBar() {
    const {isActive} = useAnnouncementBar();
    const [showAnnouncementBar, setShowAnnouncementBar] = useState(isActive);
    useScrollPosition(
        ({scrollY}) => {
            if (isActive) {
                setShowAnnouncementBar(scrollY === 0);
            }
        },
        [isActive],
    );
    return isActive && showAnnouncementBar;
}

function AdBody({link, title, body, bgColor}) {
    return (<a href={link} className="hover:no-underline">
        <div
            className={`${bgColor} rounded py-4 px-4 shadow-neutral-500  mt-8 mb-4`}
            data-aos="fade-in"
            data-aos-once="true"
        >
            <div className="flex flex-col md:flex-row justify-between items-center">
                <div className="text-center md:text-left">
                    <div className="text-2xl leading-snug bold text-neutral-800 -mt-1">
                        {title}
                    </div>
                    <div className="text-neutral-800 opacity-90">
                        {body}
                    </div>
                </div>
            </div>
        </div>
    </a>)
}

export default function DocSidebarDesktopContent({path, sidebar, className}) {
    const showAnnouncementBar = useShowAnnouncementBar();
    return (
        <nav
            aria-label={translate({
                id: 'theme.docs.sidebar.navAriaLabel',
                message: 'Docs sidebar',
                description: 'The ARIA label for the sidebar navigation',
            })}
            className={clsx(
                'menu thin-scrollbar',
                styles.menu,
                showAnnouncementBar && styles.menuWithAnnouncementBar,
                className,
            )}>
            <ul className={clsx(ThemeClassNames.docs.docSidebarMenu, 'menu__list')}>
                <div className="tailwind">
                    <div className="px-3 border">
                    <a href="https://touchlab.co/oss?s=shownewsletter" className="bold text-lg" target="_blank">
                        Subscribe to the <br/>Touchlab Newsletter
                    </a>

                    </div>
                    <div className="h-0.5 bg-neutral-700 mb-2"/>

                </div>
                <DocSidebarItems items={sidebar} activePath={path} level={1}/>
                <div className="tailwind">
                    <AdBody
                        link="https://touchlab.co/tlpro"
                        title="Upgrade to Touchlab Pro KMP Support"
                        body="Support and study options for individuals and teams, including self-study guides, production best practices, and live support."
                        bgColor="bg-lime-400 hover:bg-lime-300"/>
                </div>
            </ul>
        </nav>
    );
}
