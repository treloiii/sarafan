export function indexOf(arr,el){
    for(let i=0;i<arr.length;i++){
        if(arr[i].id===el.id)
            return i
    }
    return -1;
}