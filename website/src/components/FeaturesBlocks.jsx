import React, {useState} from 'react';
import {
  Box2d,
  Box3d,
  Cyborg,
  FilterOrganization, Messaging, NetworkConnection,
  Note, ThumbUp,
  WarningSign,
} from "./FeatureIcons";

function FeatureBlock(name, description, svgBody){
  return(
  <div className="relative flex flex-col items-center" data-aos="fade-up" data-aos-anchor="[data-aos-id-blocks]">
    {svgBody()}
    <h4 className="h4 mb-2">{name}</h4>
    <p className="text-lg text-gray-700 dark:text-gray-400 text-center">{description}</p>
  </div>
  )
}

export default function FeaturesBlocks() {
  const [videoModalOpen, setVideoModalOpen] = useState(false);
  return (
    <section>
      <div className="max-w-6xl mx-auto px-4 sm:px-6">
        <div className="pt-10 pb-12 md:pt-16 md:pb-20 border-t border-gray-800">

          {/* Section header */}
          <div className="max-w-3xl mx-auto text-center pb-12 md:pb-20">
            <h2 className="h2 mb-4">Easily publish Xcode Framework binaries from your shared Kotlin code</h2>
            <p className="text-xl text-gray-700 dark:text-gray-400">Simpler, low-risk Kotlin code integration</p>
          </div>

          {/* Items */}
          <div className="max-w-sm mx-auto grid gap-8 md:grid-cols-2 lg:grid-cols-3 lg:gap-16 items-start md:max-w-2xl lg:max-w-none" data-aos-id-blocks>

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
                "Simply \"flip a switch\" and build/test Kotlin locally. Supports optimized local SPM development" ,
                Cyborg
            )}

          </div>


        </div>
      </div>
    </section>
  );
}
