import React from 'react';

export default function Youtube({videoUrl}) {

  return (
      <section>
          <iframe className="w-full aspect-video" src={videoUrl} frameBorder="0"
                  allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
                  allowFullScreen></iframe>
      </section>
  );
}
