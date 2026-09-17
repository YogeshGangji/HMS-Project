const formatDate = (date: any) => {
    if (!date) return undefined;
    const d = new Date(date);


    return d.toLocaleDateString("en-GB", {
        day: "numeric",
        month: "long",
        year: "numeric"
    });
}

// console.log(formatDate("2001-08-15"));

export { formatDate };