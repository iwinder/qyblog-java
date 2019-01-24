import { CommentAgent } from "./comment-agent";
export type CommentStatus = 'APPLIED' | 'ENROLLED' | 'REFUSED' ;


export class Comment {
    id: number;
    content: string;
    author: string;
    email: string;
    url: string;
    ip: string;
    status: CommentStatus;
    target: CommentAgent;
    depth: number;
    parent: Comment;
    topParent: Comment;
    replyCount: number;
    createdDate: Date;
}
