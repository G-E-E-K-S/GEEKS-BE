package com.example.geeks.repository;

import com.example.geeks.domain.Comment;
import com.example.geeks.responseDto.PostCommentResponseDTO;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.geeks.domain.QComment.*;

@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom{
    private final EntityManager em;

    @Override
    public List<PostCommentResponseDTO> findByPostId(Long postId) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        List<Comment> comments = queryFactory
                .selectFrom(comment)
                .leftJoin(comment.parent).fetchJoin()
                .where(comment.post.id.eq(postId))
                .orderBy(comment.parent.id.asc().nullsFirst(),
                        comment.createdDate.asc())
                .fetch();

        List<PostCommentResponseDTO> result = new ArrayList<>();
        Map<Long, PostCommentResponseDTO> commentResponseDTOMap = new HashMap<>();

        comments.forEach(comment -> {
            PostCommentResponseDTO responseDTO =
                    new PostCommentResponseDTO(
                            comment.getId(),
                            comment.getMember().getNickname(),
                            comment.getContent(),
                            comment.getIsDeleted(),
                            comment.getCreatedDate());

            commentResponseDTOMap.put(responseDTO.getCommentId(), responseDTO);

            if(comment.getParent() != null) {
                commentResponseDTOMap.get(comment.getParent().getId()).getChildren().add(responseDTO);
            } else {
                result.add(responseDTO);
            }
        });

        return result;
    }
}
