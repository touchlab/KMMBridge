import React from 'react';

export default function Video({videoUrl}) {
    return (
        <section>
            <video controls className="w-full aspect-video">
                <source src={videoUrl} type="video/mp4"/>
                Your browser does not support the video tag.
            </video>
        </section>
    );
}
