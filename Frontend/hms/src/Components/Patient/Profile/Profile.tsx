import { Avatar, Button, Divider, Modal, NumberInput, Select, Table, TagsInput, TextInput } from '@mantine/core'
import { IconEdit } from '@tabler/icons-react';
import { useEffect, useState } from 'react';
import { useSelector } from 'react-redux'
import { DateInput } from "@mantine/dates"
import { bloodGroups } from '../../../Data/DropDownData';
import { useDisclosure } from '@mantine/hooks';
import { getPaitient, updatePatient } from '../../../Service/PatientProfileService';
import { formatDate } from '../../../Utility/DateUtility';
import { useForm } from '@mantine/form';
import { errorNotification, successNotification } from '../../../Utility/NotificationUtil';
const Profile = () => {
    
    const [editMode, setEdit] = useState(false);
    const [opened, { open, close }] = useDisclosure(false);
    const user = useSelector((state: any) => state.user)
    const [profile, setProfile] = useState<any>({});

    const form = useForm({
        initialValues: {
            dob: '',
            phone: '',
            address: '',
            aadharNo: '',
            bloodGroup: '',
            allergies: '',
            chronicDisease: ''
        },
        validate: {
            dob: (value) => !value ? "Date of Birth is Required " : null,
            phone: (val) => !val ? "phone number is requied" : null,
            address: (val) => !val ? "Address is requied" : null,
            aadharNo: (val) => !val ? "Aadhar number is requied" : null,

        }
    });

    useEffect(() => {
        getPaitient(user.profileId).then((data) => {
            setProfile(data);
            form.setValues({
                dob: data.dob ?? '',
                phone: data.phone ?? '',
                address: data.address ?? '',
                aadharNo: data.aadharNo ?? '',
                bloodGroup: data.bloodGroup ?? '',
                allergies: data.allergies ?? [],
                chronicDisease: data.chronicDisease ?? []
            });
        }).catch((error) => console.log(error));
    }, [user.profileId]);

    const handleSubmit = (values: any) => {

        updatePatient({ ...profile, ...values }).then((data) => {
            successNotification("Profile Updated Succefully");
            setProfile(data);
            setEdit(false);

        }).catch((error) => {
            errorNotification(error.response.data.errorMessage);
            console.log(error);
        })
    }

    return (

        <form onSubmit={form.onSubmit(handleSubmit, (errors) => console.log('Validation errors:', errors))} className='p-10'>
            <div className=" flex  justify-between  items-center">
                <div className="flex gap-5 items-center">

                    <div className="flex flex-col item-center gap-3 ">

                        <Avatar variant='filled' src="/avtar.png" size={150} alt='its me ' />

                        {editMode && <Button size='sm' onClick={open} variant='filled' >Upload</Button>
                        }
                    </div>

                    <div className="flex flex-col gap-3">
                        <div className="text-3xl font-medium text-neutral-900">{user.name}</div>
                        <div className="text-xl text-neutral-700">{user.email}</div>
                    </div>

                </div>
                {!editMode &&
                    <Button type="button" size='md' onClick={() => setEdit(true)} variant='filled' leftSection={<IconEdit />}>Edit</Button>}
                {editMode &&
                    <Button size='md' type="submit" variant='filled' >Save</Button>
                }
            </div>


            <Divider my="xl" />
            <div className="">

                <div className="text-2xl font-medium mb-5 text-neutral-900">Personal Information</div>
                <Table
                    striped
                    stripedColor='primary.1'
                    withRowBorders={false}
                    verticalSpacing="md"
                >
                    <Table.Tbody className="[&>tr]:!mb-3 [&_td]:!w-1/2">

                        <Table.Tr>
                            <Table.Td className="font-semibold text-xl">Date of Birth</Table.Td>
                            {editMode ? (
                                <Table.Td className="text-xl">
                                    <DateInput {...form.getInputProps("dob")} placeholder="Enter date of birth" />
                                </Table.Td>
                            ) : (
                                <Table.Td className="text-xl">{formatDate(profile.dob) ?? "-"}</Table.Td>
                            )}
                        </Table.Tr>

                        <Table.Tr>
                            <Table.Td className="font-semibold text-xl">Phone</Table.Td>
                            {editMode ? (
                                <Table.Td className="text-xl">
                                    <NumberInput {...form.getInputProps("phone")} hideControls maxLength={10} clampBehavior='strict' placeholder="Enter phone number" />
                                </Table.Td>
                            ) : (
                                <Table.Td className="text-xl">{profile.phone ?? "-"}</Table.Td>
                            )}
                        </Table.Tr>

                        <Table.Tr>
                            <Table.Td className="font-semibold text-xl">Address</Table.Td>
                            {editMode ? (
                                <Table.Td className="text-xl">
                                    <TextInput {...form.getInputProps("address")} placeholder="Enter address" />
                                </Table.Td>
                            ) : (
                                <Table.Td className="text-xl">{profile.address ?? "-"}</Table.Td>
                            )}
                        </Table.Tr>

                        <Table.Tr>
                            <Table.Td className="font-semibold text-xl">Aadhar No</Table.Td>
                            {editMode ? (
                                <Table.Td className="text-xl">
                                    <NumberInput {...form.getInputProps("aadharNo")} placeholder="Enter Aadhar number" hideControls
                                        clampBehavior='strict' maxLength={12} />
                                </Table.Td>
                            ) : (
                                <Table.Td className="text-xl">{profile.aadharNo ?? "-"}</Table.Td>
                            )}
                        </Table.Tr>

                        <Table.Tr>
                            <Table.Td className="font-semibold text-xl">Blood Group</Table.Td>
                            {editMode ? (
                                <Table.Td className="text-xl">
                                    <Select
                                        {...form.getInputProps("bloodGroup")}
                                        placeholder="Select blood group"
                                        data={bloodGroups}
                                        searchable
                                        clearable

                                    />
                                </Table.Td>
                            ) : (
                                <Table.Td className="text-xl">
                                    {bloodGroups.find((group) => group.value === profile.bloodGroup)?.label ?? profile.bloodGroup ?? "-"}
                                </Table.Td>
                            )}
                        </Table.Tr>

                        <Table.Tr>
                            <Table.Td className="font-semibold text-xl">Allergies</Table.Td>
                            {editMode ? (
                                <Table.Td className="text-xl">
                                    <TagsInput {...form.getInputProps("allergies")} placeholder="Enter allergies" />
                                </Table.Td>
                            ) : (
                                <Table.Td className="text-xl">{profile.allergies?.length ? profile.allergies.join(", ") : "-"}</Table.Td>
                            )}
                        </Table.Tr>

                        <Table.Tr>
                            <Table.Td className="font-semibold text-xl">Chronic Disease</Table.Td>
                            {editMode ? (
                                <Table.Td className="text-xl">
                                    <TagsInput {...form.getInputProps("chronicDisease")} placeholder="Enter chronic disease" />
                                </Table.Td>
                            ) : (
                                <Table.Td className="text-xl">
                                    {profile.chronicDisease?.length
                                        ? profile.chronicDisease.join(", ")
                                        : "-"}
                                </Table.Td>)}
                        </Table.Tr>

                    </Table.Tbody>
                </Table >
            </div >
            <Modal centered opened={opened} onClose={close} title={<span className='text-xl font-medium'>Upload Profile Picture</span>}>

            </Modal>

        </form >
    )
}

export default Profile