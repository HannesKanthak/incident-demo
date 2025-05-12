export function formatRelativeTime(dateString: string): string {
    const diff = Date.now() - new Date(dateString).getTime()
    const minutes = Math.floor(diff / (1000 * 60))
    if (minutes < 1) return 'gerade eben'
    if(minutes == 1) return 'vor 1 Minute'
    if (minutes < 60) return `vor ${minutes} Minuten`
    const hours = Math.floor(minutes / 60)
    if(hours == 1) return 'vor 1 Stunde'
    if (hours < 24) return `vor ${hours} Stunden`
    const days = Math.floor(hours / 24)
    if(days == 1) return 'vor 1 Tag'
    return`vor ${days} Tagen`
}
