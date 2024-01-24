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
                style={{ flex: 1 }}
            />
        ) : null
    );
};

export default WebViewScreen;
