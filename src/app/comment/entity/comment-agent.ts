export class CommentAgent {
    id: number;
    /**
     * 评论对象id
     */
    targetId: number;
    /**
     * 评论对象名称
     */
    targetName: string;

    /**
     * 评论总数
     */

    commentCount: number;

    /**
     * 评论是否可用
     */

    isEnabled: Boolean;

}
