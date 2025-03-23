import React, { useState } from 'react';
import { Button, Text, View } from 'react-native';

const PostingScreen = ({ route }) => {
    const { postInfo } = route.params; // 게시물 정보

    const handlePosting = () => { // 게시물 작성 버튼 누르면 호출
        // 게시물 작성, API 호출
        console.log(postInfo);
    };

    return (
        <View>
            <Text>카테고리: {postInfo.category}</Text>
            <Text>제목: {postInfo.title}</Text>
            <Text>내용: {postInfo.content}</Text>
            <Text>참가 인원: {postInfo.match.headCnt}</Text>
            <Text>장소: {postInfo.match.place}</Text>
            <Text>시작 시간: {postInfo.match.startTime}</Text>
            <Text>종료 시간: {postInfo.match.endTime}</Text>
            <Text>연령 유형: {postInfo.tag.ageType}</Text>
            <Text>성별 유형: {postInfo.tag.genderType}</Text>
            <Text>레벨 유형: {postInfo.tag.levelType}</Text>
            <Button title="게시물 작성" onPress={handlePosting} />
        </View>
    );
};

export default PostingScreen;
