import { CommentAgent } from "./comment-agent";

export class Comment {
    id: number;
    content: string;
    author: string;
    email: String;
    url: string;
    replyCount: string;
    parent: Comment;
    target: CommentAgent;
    createdDate: Date;
    agentTargetId: number;
    parentId: number;
}
