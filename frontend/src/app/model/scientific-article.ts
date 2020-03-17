import { Author } from './author';

export class ScientificArticle {
    id: number;
    authors: Author[];
    title: string;
    publisher: string;
    publishDate: any;
    keyWords: string[];
}
