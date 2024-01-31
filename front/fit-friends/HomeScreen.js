import React from 'react';
import { Text, StyleSheet, View, TouchableOpacity, Image } from 'react-native';
import { createStackNavigator } from '@react-navigation/stack';


const Stack = createStackNavigator();

const HomeScreen = ({ navigation }) => {
    const login = async () => {
        navigation.navigate('WebViewScreen', { uri: 'http://fit-friends.duckdns.org:8081/api/login' });
    };

    return (
        <View style={styles.container}>
            <View style={styles.textContainer}>
                <Text style={styles.subtitleTextStyle}>
                    {'우리 같이 운동 해요'}
                </Text>
                <Text style={[styles.titleTextStyle, { marginTop: 8 }]}>
                    {'피트\n프렌즈'}
                </Text>
            </View>
            <View style={styles.imageContainer}>
                <TouchableOpacity
                    style={[styles.buttonStyle, { marginBottom: 18 }]}
                    onPress={login}
                >
                    <Image source={require('./assets/google.png')} style={styles.buttonImageStyle} />
                    <Text style={styles.buttonTextStyle}>로그인하여 시작하기</Text>
                </TouchableOpacity>
                <Image source={require('./assets/home-image.png')} style={styles.imageStyle} />
            </View>
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
    },
    textContainer: {
        marginTop: 130,
        justifyContent: 'flex-start',
        alignItems: 'flex-start',
        paddingLeft: 30,
    },
    imageContainer: {
        marginBottom: 51,
        justifyContent: 'flex-start',
        alignItems: 'center',
        marginTop: 70,
    },
    subtitleTextStyle: {
        color: '#4CAF50',
        fontFamily: 'Roboto',
        fontSize: 24,
        fontWeight: '400',
        lineHeight: 28,
        flexShrink: 0,
    },
    titleTextStyle: {
        color: '#4CAF50',
        fontFamily: 'Roboto',
        fontSize: 64,
        fontWeight: '700',
        lineHeight: 68,
        flexShrink: 0,
    },
    buttonTextStyle: {
        color: '#FFFFFF',
        fontFamily: 'Roboto',
        fontSize: 16,
        fontWeight: '400',
        lineHeight: 20,
        flexShrink: 0,
    },
    buttonStyle: {
        width: 195,
        height: 48,
        borderRadius: 50,
        backgroundColor: '#4CAF50',
        justifyContent: 'center',
        alignItems: 'center',
        flexDirection: 'row',
        flexShrink: 0,
    },
    buttonImageStyle: {
        marginRight: 10,
    },
    buttonTextStyle: {
        color: 'white',
        fontSize: 16,
    },
    imageStyle: {
        width: 306,
        height: 316,
        flexShrink: 0,
    },
});

export default HomeScreen;