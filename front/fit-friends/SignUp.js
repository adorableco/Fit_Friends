import React from 'react';
import { Text, StyleSheet } from 'react-native';

const SignUp = () => {
    return (
        <Text style={styles.textStyle}>
            회원가입
        </Text>
    );
};

const styles = StyleSheet.create({
    textStyle: {
        width: 240,
        height: 84,
        color: '#4CAF50',
        fontFamily: 'Roboto',
        fontSize: 64,
        fontWeight: '400',
        flexShrink: 0,
    },
});

export default SignUp;
