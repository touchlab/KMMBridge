import React from 'react';

// https://fshoq.com/free-photos/p/114/perspective-wooden-bridge-and-the-sea
import HeroImage from '@site/static/img/bridge.jpg';

function HeroAbout() {
  return (
    <section className="relative">

      {/* Background image */}
      <div className="absolute inset-0">
              <img className="w-full h-full object-cover" src={HeroImage} width="1440" height="394" alt="About" />
        <div className="absolute inset-0 bg-gray-900 opacity-60" aria-hidden="true"></div>
      </div>

      {/* Hero content */}
      <div className="max-w-6xl mx-auto px-4 sm:px-6 relative">
        <div className="pt-32 pb-12 md:pt-40 md:pb-20">
          <div className="max-w-6xl mx-auto text-center">
            <h1 className="h1 mb-4 drop-shadow-lg text-white" data-aos="fade-up">KMMBridge - Xcode Framework Publishing</h1>
            <p className="text-xl text-gray-300 mb-8 drop-shadow-lg" data-aos="fade-up" data-aos-delay="200">Kotlin Multiplatform binary publishing for teams</p>
              <div className="max-w-xs mx-auto sm:max-w-none sm:flex sm:justify-center">
                  <div data-aos="fade-up" data-aos-delay="400">
                      <a className="btn text-white bg-sky-600 hover:bg-sky-700 w-full mb-4 sm:w-auto sm:mb-0 drop-shadow-lg" href="docs/">Get Started</a>
                  </div>
                  <div data-aos="fade-up" data-aos-delay="600">
                      <a className="btn text-white bg-gray-700 hover:bg-gray-800 w-full sm:w-auto sm:ml-4 drop-shadow-lg" href="https://github.com/touchlab/KMMBridge">Open Github</a>
                  </div>
              </div>
          </div>
        </div>
      </div>

    </section>
  );
}

export default HeroAbout;