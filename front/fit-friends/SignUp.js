import React, { useState } from 'react';
import { Text, StyleSheet, Image, TouchableOpacity, View } from 'react-native';
import ImagePicker from 'react-native-image-picker';

const SignUp = () => {
    const [imageSource, setImageSource] = useState(null);

    const profileImage = () => {
        const [imageSource, setImageSource] = useState(null);

        const selectImage = () => {
            ImagePicker.showImagePicker({}, response => {
                if (response.uri) {
                    setImageSource(response.uri);
                }
            });
        };

        ImagePicker.showImagePicker(options, (response) => {
            if (response.didCancel) {
                console.log('User cancelled image picker');
            } else if (response.error) {
                console.log('ImagePicker Error: ', response.error);
            } else {
                const source = { uri: "https://source.unsplash.com/random" };
                setImageSource(source);
            }
        });
    };

    return (
        <View>
            <Text style={styles.titleTextStyle}>
                회원가입
            </Text>,
            {imageSource &&
                <Image
                    source={{ uri: profileImage }}
                    style={styles.buttonImageStyle}
                />
            }
            <TouchableOpacity
                style={[styles.buttonStyle, { marginTop: 240 }]}
                onPress={profileImage}>
                <Text style={styles.greenTextStyle}>이미지 변경</Text>
            </TouchableOpacity>
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

export default SignUp;
