import React, { useState, useEffect } from 'react';
import { View, Text, FlatList, TouchableOpacity, Image } from 'react-native';

const PostsScreen = () => {
  const [data, setData] = useState([]);

  useEffect(() => {
    fetchPosts();
  }, []);

  const fetchPosts = async () => {
    // API 호출
    const response = await fetch('http://localhost:8080/api/posts', {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      },
    });
    const json = await response.json();

    setData(json);
  };

  const renderItem = ({ item }) => (
    <View style={{ padding: 10, borderBottom: '1px solid #ddd' }}>
      <Text style={{ fontSize: 18, fontWeight: 'bold' }}>{item.title}</Text>
      <Text>{item.category}</Text>
      <Text>{item.userName}</Text>
      <Text>{item.createdDate}</Text>
    </View>
  );

  return (
    <View style={{ flex: 1, backgroundColor: '#fff' }}>
      <FlatList
        data={data}
        renderItem={renderItem}
        keyExtractor={(item) => item.postId.toString()}
      />
    </View>
  );
};

export default PostsScreen;
