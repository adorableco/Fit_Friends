import React, { useEffect, useState } from 'react';
import { WebView } from 'react-native-webview';
import axios from 'axios';
import { useNavigation } from '@react-navigation/native';

const WebViewScreen = () => {
    const [url, setUrl] = useState(null);
    const [accessToken, setAccessToken] = useState(null);
    const navigation = useNavigation();

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await axios.get('http://fit-friends.duckdns.org:8081/api/login');
                setUrl(response.data);
                setAccessToken(response.data.accessToken);
            } catch (error) {
                console.error('Failed to fetch data:', error);
            }
        };

        fetchData();
    }, []);

    const handleNavigationStateChange = (navState) => {
        if (accessToken == null) {
            navigation.navigate('SignUpScreen');
        }
    };

    return (
        url ? (
            <WebView
                source={{ uri: url }}
                onNavigationStateChange={handleNavigationStateChange}
                userAgent='Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36'
                style={{ flex: 1 }}
            />
        ) : null
    );
};

export default WebViewScreen;
