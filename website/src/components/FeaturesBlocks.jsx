import React, {useState} from 'react';
import {
    Box2d, Box2dCyan,
    Box3d, Box3dCyan,
    Cyborg, CyborgCyan,
    FilterOrganization, FilterOrganizationCyan, Messaging, NetworkConnection, NetworkConnectionCyan,
    Note, ThumbUp, ThumbUpCyan,
    WarningSign,
} from "./FeatureIcons";

function FeatureBlock(name, description, svgBody) {
    return (
        <div className="relative flex flex-col items-center mb-2">
            {svgBody()}
            <h4 className="h4 mb-2">{name}</h4>
            <p className="text-lg text-gray-700 dark:text-gray-400 text-center">{description}</p>
        </div>
    )
}

function LinkFeatureBlock(name, description, link, svgBody) {
    return (
        <a href={link} className="home-link">
            <div className="relative flex flex-col items-center mb-2">
                {svgBody()}
                <h4 className="h4 mb-2">{name} <svg width="20" height="20" aria-hidden="true" viewBox="0 0 24 24" className="svg-inline iconExternalLink_node_modules-@docusaurus-theme-classic-lib-theme-Icon-ExternalLink-styles-module"><path fill="currentColor" d="M21 13v10h-21v-19h12v2h-10v15h17v-8h2zm3-12h-10.988l4.035 4-6.977 7.07 2.828 2.828 6.977-7.07 4.125 4.172v-11z"></path></svg></h4>
                <p className="text-lg text-center">{description}</p>
            </div>
        </a>
    )
}

export default function FeaturesBlocks() {
    const [videoModalOpen, setVideoModalOpen] = useState(false);
    return (
        <>
            <section>
                <div className="max-w-6xl mx-auto px-4 py-12">
                    <div className="max-w-3xl mx-auto text-center">
                        <h2 className="h2 mb-4">Easily publish Xcode Framework binaries from your shared Kotlin
                            code</h2>
                        <p className="text-xl text-gray-700 dark:text-gray-400">Simpler, low-risk Kotlin code
                            integration</p>
                    </div>
                </div>
            </section>

            <section id="overlayContainer" className="bg-slate-100 dark:bg-neutral-900">
                <div className="max-w-6xl mx-auto px-4 py-12">
                    <div className="max-w-3xl mx-auto text-center">
                        <h3 className="h3">Features</h3>
                    </div>

                    {/* Items */}
                    <div
                        className="max-w-sm mx-auto grid gap-8 md:grid-cols-2 lg:grid-cols-3 lg:gap-16 items-start md:max-w-2xl lg:max-w-none"
                        data-aos-id-blocks>

                        {FeatureBlock(
                            "Package Managers",
                            "CocoaPods and SPM support, with private or public publish locations",
                            Box2d
                        )}

                        {FeatureBlock(
                            "No-friction Integration",
                            "Avoid disrupting iOS dev flow by integrating a pre-built binary, with simple config and automated deployment",
                            ThumbUp
                        )}

                        {FeatureBlock(
                            "Local Development",
                            "Simply \"flip a switch\" and build/test Kotlin locally. Supports optimized local SPM development",
                            Cyborg
                        )}

                    </div>
                </div>
            </section>

            <section>
                <div className="max-w-6xl mx-auto px-4 py-12">
                    <div className="">
                        <div className="max-w-3xl mx-auto text-center">
                            <h3 className="h3" id="resources">Resources</h3>
                        </div>

                        {/* Items */}
                        <div
                            className="max-w-sm mx-auto grid gap-8 md:grid-cols-2 lg:grid-cols-3 lg:gap-16 items-start md:max-w-2xl lg:max-w-none"
                            data-aos-id-blocks>

                            {LinkFeatureBlock(
                                "KMP Team Workflows",
                                "Team workflows with Kotlin Multiplatform vary in structure. KMMBridge provides the flexibility to support different configurations.",
                                "https://touchlab.co/kmmbridge-team-workflows",
                                Box3dCyan
                            )}

                            {LinkFeatureBlock(
                                "KMMBridge Quick Start",
                                "For native mobile teams looking to publish from a shared Kotlin repo, our template project can have you up and running in minutes.",
                                "https://touchlab.co/kmmbridge-quick-start",
                                FilterOrganizationCyan
                            )}

                            {LinkFeatureBlock(
                                "Evaluating KMP for Teams",
                                "KotlinConf 2023 talk \"KMP mobile for Teams\" where we discuss how teams approach and evaluate KMP.",
                                "https://www.youtube.com/watch?v=-tJvCOfJesk",
                                NetworkConnectionCyan
                            )}

                        </div>


                    </div>
                </div>
            </section>
        </>
    );
}
