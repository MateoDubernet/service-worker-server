self.addEventListener('install', () => {
    self.skipWaiting();
})

// self.addEventListener('activate', (event) => { 
//     console.log('[Service Worker] Activation'); 
//     event.waitUntil(
//         self.clients.claim()
//     );
// });

self.addEventListener('push', (event) => {
    const data = event.data ? event.data.json() : {};
    console.log('********************************** ZZZZZZZZZ');
    
    event.waitUntil(
        self.registration.showNotification(data.title, {
            body: data.message,
            data: data,
        })
    )
})

self.addEventListener('notificationclick', (event) => {
    console.log('------------------ZZZZZZZZZZ' + event.notification.data);
})

