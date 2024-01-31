import React, { useEffect, useState } from 'react';
import { WebView } from 'react-native-webview';
import axios from 'axios';

const WebViewScreen = () => {
    const [url, setUrl] = useState(null);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await axios.get('http://fit-friends.duckdns.org:8081/api/login');
                setUrl(response.data);
            } catch (error) {
                console.error('Failed to fetch data:', error);
            }
        };

        fetchData();
    }, []);

    return (
        url ? (
            <WebView
                source={{ uri: url }}
                userAgent='Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36'
                style={{ flex: 1 }}
            />
        ) : null
    );
};

export default WebViewScreen;
