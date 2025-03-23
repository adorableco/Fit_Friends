import React, { useState, useEffect } from 'react';
import { View, Text, Image } from 'react-native';
import { useRoute } from '@react-navigation/native';

const DetailScreen = () => {
    const [data, setData] = useState(null);
    const route = useRoute();
    const postId = route.params.postId;

    const fetchPostDetail = async () => {
        const response = await fetch(`http://3.39.127.227:8081/api/post/1`);
        const json = await response.json();

        setData(json);
    };

    useEffect(() => {
        fetchPostDetail();
    }, []);

    if (!data) {
        return <Text>Loading...</Text>;
    }

    return (
        <View style={{ padding: 10 }}>
            <Text style={{ fontSize: 24, fontWeight: 'bold' }}>{data.title}</Text>
            <Text>{data.category}</Text>
            <Image source={{ uri: data.user.profileImage }} style={{ width: 100, height: 50 }} />
            <Text>{data.content}</Text>
            <Text>{data.createdDate}</Text>
            <Text>{data.match.title}</Text>
            <Text>{data.match.endTime}</Text>
            <Text>{data.currentHeadCnt} / {data.match.headCnt}</Text>
        </View>
    );
};

export default DetailScreen;