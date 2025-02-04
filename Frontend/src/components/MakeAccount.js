function MakeAccount({type}) {
    let InputComponent = null;
    if (type === "Customer"){
        InputComponent = CustomerInput
    }else if (type ==="Owner"){
        InputComponent = OwnerInput
    }

    return <>
    <InputComponent/>
    </>
}
export default MakeAccount;