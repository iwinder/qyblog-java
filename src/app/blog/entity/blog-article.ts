import { User } from "../../system/entity/user";

export class BlogArticle {
    id: number;
    title: string;
    content: string;
    contentHtml: string;
    permaLink: string;
    author: User;
    thumbnail: string;
    publishedDate: Date;
    isPublished: boolean;
    isDeleted: boolean;
}
