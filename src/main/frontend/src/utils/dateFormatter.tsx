import { formatDistance, subDays } from "date-fns";

export const dateFormatter = {
  formatDate,
  dateDistance,
};

function formatDate(dateString: string) {
  const date = new Date(dateString);
  return date.toLocaleDateString("en-US", {
    year: "numeric",
    month: "long",
    day: "numeric",
  });
}

function dateDistance(dateString: string) {
  return formatDistance(subDays(new Date(dateString), 0), new Date(), {
    addSuffix: true,
  });
}
