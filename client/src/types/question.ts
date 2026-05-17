export type Question = {
  id: number;
  title: string;
  content: string;
  resolved: boolean;
  bestAnswerId: number | null;
  createdAt: string;
  answerCount: number;
  userId: number;
  displayName: string;
  homepageKey: string;
};

export type Answer = {
  id: number;
  questionId: number;
  content: string;
  createdAt: string;
  userId: number;
  displayName: string;
  homepageKey: string;
};