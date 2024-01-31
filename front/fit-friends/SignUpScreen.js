import React from 'react';
import { Text, StyleSheet, Image, View } from 'react-native';

const SignUpScreen = ({ route }) => {
    const { name, email, picture } = route.params;

    return (
        <View>
            <Text>이름: {name}</Text>
            <Text>이메일: {email}</Text>
            <Text>프로필 사진 URL: {picture}</Text>
        </View>
    );
};

const styles = StyleSheet.create({
    titleTextStyle: {
        width: 240,
        height: 84,
        color: '#4CAF50',
        fontFamily: 'Roboto',
        fontSize: 64,
        fontWeight: '400',
        flexShrink: 0,
    },
    greenTextStyle: {
        width: 161,
        height: 23,
        color: '#4CAF50',
        fontFamily: 'Roboto',
        fontSize: 16,
        fontWeight: '400',
        flexShrink: 0,
    },
    imageStyle: {
        marginTop: 217,
        paddingLeft: 37,
        width: 75,
        height: 75,
        borderRadius: 100,
        backgroundColor: '#4CAF50',
        //justifyContent: 'center',
        //alignItems: 'center',
        //flexDirection: 'row',
        flexShrink: 0,
        shadowColor: "#000",
        shadowOffset: {
            width: 0,
            height: 4,
        },
        shadowOpacity: 0.25,
        shadowRadius: 4,
        elevation: 5,
    },
    buttonStyle: {
        width: 92,
        height: 37,
        borderRadius: 50,
        backgroundColor: '#FFFFFF',
        borderWidth: 1,
        borderColor: '#4CAF50',
        justifyContent: 'center',
        alignItems: 'center',
        flexDirection: 'row',
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
});

export default SignUpScreen;
